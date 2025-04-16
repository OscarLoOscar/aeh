package com.example.dimerco.hawb.aehawb.service;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.dimerco.hawb.aehawb.model.BookingRequest;
import com.example.dimerco.hawb.aehawb.model.InputData;
import com.example.dimerco.hawb.aehawb.model.AehawbMapper;
import com.google.cloud.aiplatform.v1.Content;
import com.google.cloud.aiplatform.v1.FileData;
import com.google.cloud.aiplatform.v1.GenerateContentRequest;
import com.google.cloud.aiplatform.v1.GenerateContentResponse;
import com.google.cloud.aiplatform.v1.GenerationConfig;
import com.google.cloud.aiplatform.v1.Part;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GeminiPdfProcessor {

    private static final String PROJECT_ID = "credible-spark-454201-n2";
    private static final String LOCATION = "us-east1";
    private static final String PUBLISHER = "google";
    private static final String MODEL = "gemini-2.0-flash-001";
    private static final String BUCKET_NAME = "dimhkg_dimbot";
    private static final String RESULT_PATH = "C:\\Users\\hkhkgda03\\Desktop\\Dimerco\\BITMeeting2025\\DIMHKG_AE_HAWB\\TestingPdf\\result";
    private static final String GOOGLE_APPLICATION_CREDENTIALS_PATH = "C:\\path\\to\\your\\service-account-key.json";

    private static final String BOOKING_FILE_NAME = "JPN1-GS01 (KOIKE)_Booking (Dimerco)_O-6896";
    private static final String INVOICE_FILE_NAME = "JPN1-GS01_Invoice & PL (O-6896)";

    @Autowired
    private AehawbMapper mapper;

    private PredictionServiceClient predictionClient;
    private Storage storage;

    private String uploadedBookingObjectName;
    private String uploadedInvoiceObjectName;

    public static void main(String[] args) throws Exception {
        new GeminiPdfProcessor().process();
    }

    public void process() throws Exception {
        init();
        uploadFilesToGCS();
        generateContentFromGeminiAPI();
    }

    private void init() {
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", GOOGLE_APPLICATION_CREDENTIALS_PATH);
        try {
            predictionClient = PredictionServiceClient.create();
            storage = StorageOptions.getDefaultInstance().getService();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize PredictionServiceClient or Storage", e);
        }
    }

    private void uploadFilesToGCS() throws Exception {
        String bookingFilePath = getLocalFilePath(BOOKING_FILE_NAME);
        String invoiceFilePath = getLocalFilePath(INVOICE_FILE_NAME);

        uploadedBookingObjectName = uploadFileToGCS(bookingFilePath, "booking");
        uploadedInvoiceObjectName = uploadFileToGCS(invoiceFilePath, "invoice");

        System.out.println("Files uploaded to Google Cloud Storage successfully.");
    }

    private String getLocalFilePath(String fileName) {
        return "C:\\Users\\hkhkgda03\\Desktop\\Dimerco\\BITMeeting2025\\DIMHKG_AE_HAWB\\TestingPdf\\" + fileName + ".pdf";
    }

    private String uploadFileToGCS(String filePath, String fileType) throws Exception {
        String objectName = "pdfs/" + UUID.randomUUID() + "_" + fileType + ".pdf";
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").build();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
        System.out.println(fileType.substring(0, 1).toUpperCase() + fileType.substring(1) + " PDF Uploaded to GCS: " + blob.getName());
        return objectName;
    }

    private void generateContentFromGeminiAPI() throws Exception {
        String bookingPrompt = createBookingPrompt();
        String invoicePrompt = createInvoicePrompt();

        String gcsBookingPath = "gs://" + BUCKET_NAME + "/" + uploadedBookingObjectName;
        String gcsInvoicePath = "gs://" + BUCKET_NAME + "/" + uploadedInvoiceObjectName;

        Part bookingTextPart = Part.newBuilder().setText(bookingPrompt).build();
        Part bookingFilePart = createFilePart(gcsBookingPath);
        Part invoiceTextPart = Part.newBuilder().setText(invoicePrompt).build();
        Part invoiceFilePart = createFilePart(gcsInvoicePath);

        Content content = Content.newBuilder().setRole("user")
                .addParts(bookingTextPart)
                .addParts(bookingFilePart)
                .addParts(invoiceTextPart)
                .addParts(invoiceFilePart)
                .build();

        GenerationConfig generationConfig = GenerationConfig.newBuilder().build();
        GenerateContentRequest request = generateRequest(content, generationConfig);

        System.out.println("Sending request to Gemini API...");
        GenerateContentResponse response = predictionClient.generateContent(request);
        processResponse(response);
    }

    private String createBookingPrompt() {
        return "Process the attached booking file to extract cargo details...";
    }

    private String createInvoicePrompt() {
        return "Extract data from the 'Commercial Invoice' documents...";
    }

    private Part createFilePart(String fileUri) {
        return Part.newBuilder().setFileData(
                FileData.newBuilder().setMimeType("application/pdf").setFileUri(fileUri).build()
        ).build();
    }

    private GenerateContentRequest generateRequest(Content content, GenerationConfig generationConfig) {
        return GenerateContentRequest.newBuilder()
                .setModel(getGeminiModelPath())
                .addContents(content)
                .setGenerationConfig(generationConfig)
                .build();
    }

    private String getGeminiModelPath() {
        return String.format("projects/%s/locations/%s/publishers/%s/models/%s", PROJECT_ID, LOCATION, PUBLISHER, MODEL);
    }

    private void processResponse(GenerateContentResponse response) throws Exception {
        if (response.getCandidatesCount() > 0 && response.getCandidates(0).getContent().getPartsCount() > 0) {
            String responseText = response.getCandidates(0).getContent().getParts(0).getText();
            System.out.println("Response: " + responseText);

            if (responseText != null && !responseText.isEmpty()) {
                try {
                    Type listType = new TypeToken<List<InputData>>() {}.getType();
                    List<InputData> inputDataList = new Gson().fromJson(responseText, listType);

                    BookingRequest bookingRequest = mapper.mapInputToBookingRequest(inputDataList);
                    System.out.println("Booking Request: " + bookingRequest);

                    JsonElement jsonElement = JsonParser.parseString(responseText);
                    saveResultToFile(jsonElement);
                } catch (JsonSyntaxException e) {
                    handleJsonError(responseText, e);
                }
            } else {
                System.out.println("Empty response received from the API.");
            }
        } else {
            System.out.println("Invalid API response structure.");
        }
    }

    private void saveResultToFile(JsonElement jsonElement) throws Exception {
        Path resultDir = Paths.get(RESULT_PATH);
        if (!Files.exists(resultDir)) {
            Files.createDirectories(resultDir);
        }
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String resultFile = RESULT_PATH + File.separator + "Booking_Invoice_Result_" + timestamp + ".json";
        Files.write(Paths.get(resultFile), new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement).getBytes());
        System.out.println("Result saved to: " + resultFile);
    }

    private void handleJsonError(String responseText, JsonSyntaxException e) throws Exception {
        System.out.println("Invalid JSON format received: " + e.getMessage());
        String errorFile = RESULT_PATH + File.separator + "Booking_Invoice_Error_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
        Files.write(Paths.get(errorFile), ("INVALID JSON RESPONSE:\n" + responseText + "\n\nERROR:\n" + e.getMessage()).getBytes());
    }
}
