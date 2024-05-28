```java
//Generates qr code from data returns base64 image as string
@Component
public class QRCodeGenerator {

    public String generate(String data, Integer width, Integer height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE,width,height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        String qrcode = Base64.getEncoder().encodeToString(pngData);
        return qrcode;
    }
}
```
