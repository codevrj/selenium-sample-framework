package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.message.Message;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extentReport;
    public ExtentTest test;

    String reportName;

    public void onStart(ITestContext testContext) {

//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//		Date date = new Date();
//		String currentDateTimestamp = dateFormat.format(date);


        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
        reportName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName);// specify location of the report

        sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report"); // Title of report
        sparkReporter.config().setReportName("OrangeHRM Functional Testing"); // name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);
        extentReport.setSystemInfo("Application", "OrangeHRM");
        extentReport.setSystemInfo("Module", "Admin");
        extentReport.setSystemInfo("Sub Module", "Customers");
        extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReport.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");  // get current OS from testng xml file
        extentReport.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser"); // get current Browser from testng xml file
        extentReport.setSystemInfo("Browser", browser);

        // Group testing names into List.   if no groups are included, it will be empty
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty()) {
            extentReport.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result) {

        test = extentReport.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // to display groups in report
        test.log(Status.PASS,result.getName()+" got successfully executed");

    }

    public void onTestFailure(ITestResult result) {
        test = extentReport.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL,result.getName()+" got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        test = extentReport.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName()+" got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {

        extentReport.flush();

        //To open report on desktop automatically
        String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+reportName;
        File extentReport = new File(pathOfExtentReport);

        /*
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        //To send email with attachment
        //sendEmail(sender email,sender password(encrypted),recipient email);
        //sendEmail(xyz@gmail.com","encrypted password","abc@gmail.com");
    }



/*

    //User defined method for sending email..
    public void sendEmail(String senderEmail,String senderPassword,String recipientEmail)
    {
        // SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create a Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set the subject
            message.setSubject("Test Report with attachment");

            // Create a MimeMultipart object
            Multipart multipart = new MimeMultipart();

            // Attach the file
            String filePath = ".\\reports\\"+repName;
            String fileName = repName;

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(filePath);
            attachmentPart.setFileName(fileName);

            // Create a MimeBodyPart for the text content
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Please find the attached file.");

            // Add the parts to the multipart
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Set the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

}
