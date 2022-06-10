
````
                <dependency>
			<groupId>org.thymeleaf</groupId>
			<artifactId>thymeleaf</artifactId>
			<version>3.0.11.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.xhtmlrenderer</groupId>
			<artifactId>flying-saucer-pdf</artifactId>
			<version>9.1.20</version>
		</dependency>
    
`````

`````java
@Component
public class PDFGenerator {

    public String parseThymeleafTemplate(String templateFile, Map<String, Object> data) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        data.keySet().forEach(key->{
            context.setVariable(key,data.get(key));
        });

        return templateEngine.process(templateFile, context);
    }

    public void generatePdfFromHtml(String html, String outFile) throws IOException, DocumentException {
        String outputFolder = System.getProperty("user.home") +  File.separator + "desktop" + File.separator + outFile;
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

`````

``````java
 @Override
    public ReportData generateReport(Integer reportId, ReportData reportData) {

        PDFGenerator pdfGenerator = new PDFGenerator();
        Map<String, Object> data = new HashMap<>();
        data.put("offers",reportData);
        String str = pdfGenerator.parseThymeleafTemplate("thymeleaf_template.html",data);
        try {
            pdfGenerator.generatePdfFromHtml(str,"offer");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        return getReportData(reportId);
    }
    

``````
``````html

    <div class="offer-detail-header">
        OFFER DETAILS:
    </div>
    <div class="info-row">
        <div class="info-header">
            <p>UID: </p>
        </div>
        <div class="info">
            <p th:text="${offers.uuid}"> </p>
        </div>
    </div>
    <div class="info-row">
        <div class="info-header">
            <p> DATE:  </p>
        </div>
        <div class="info">
            <p th:text="${offers.date}"> </p>
        </div>
    </div>

 ```````
<img width="477" alt="Screen Shot 2022-06-10 at 16 37 49" src="https://user-images.githubusercontent.com/12942688/173077788-53e29d06-828c-498a-811f-732f6159c786.png">


