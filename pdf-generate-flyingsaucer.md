
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
<img width="572" alt="Screen Shot 2022-06-10 at 16 40 31" src="https://user-images.githubusercontent.com/12942688/173078335-bb31af2f-5fa9-427a-9cce-4164d8ce7c39.png">


CREATE DOWNLOADABLE PDF for Response 
`````java
   public ByteArrayOutputStream generatePdfFromHtml(String html, String outFile) throws IOException, DocumentException {
        String outputFolder = System.getProperty("user.home") +  File.separator + "desktop" + File.separator + outFile;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        return outputStream;
    }
    
  ``````
  
  ``````java
   @GetMapping(value = "/offers/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getOfferReport(@PathVariable(name = "id") Integer id){
        ReportData data = reportService.getReportData(id);

        ByteArrayOutputStream outputStream = reportService.generateReport(id,data);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=offer_report.pdf");

        InputStream in = new ByteArrayInputStream(outputStream.toByteArray());
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));

    }
    
    ```````
    
    ```````
     @Override
    public ByteArrayOutputStream generateReport(Integer reportId, ReportData reportData) {

        PDFGenerator pdfGenerator = new PDFGenerator();
        Map<String, Object> data = new HashMap<>();
        data.put("offers",reportData);
        String str = pdfGenerator.parseThymeleafTemplate("thymeleaf_template.html",data);
        try {
           return pdfGenerator.generatePdfFromHtml(str,"offer");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        
    }
```````

    
    

