package com.dariuszterefenko.reportgenerator;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//http://www.codeproject.com/Articles/986574/Android-iText-Pdf-Example
//this activity creates the pdf report to be send
public class SendReportActivity extends AppCompatActivity {
    //client id
    private int client_id;
    //access to db
    private DBHelper helper;
    private Client client;
    private File pdfFile;
    //folder to which pdf files are saved on the phone
    private static final String RAPORT_FOLDER = "/raportgenerator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(ClientViewActivity.CLIENT_ID_STR)){
            finish();
            return;
        }
        client_id = extras.getInt(ClientViewActivity.CLIENT_ID_STR);
        helper = new DBHelper(this);
        client = helper.getClient(client_id);

        try {
            //generates the report
            generateReport();
            //hides the progressbar shows the send button
            changeLayout();
        } catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }

    private void generateReport() throws DocumentException, FileNotFoundException {
        //checks for folder and if not then creates it
        File pdfFolder = new File(Environment.getExternalStorageDirectory(), RAPORT_FOLDER);
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }

        //gets the date and creates the file
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        pdfFile = new File(pdfFolder + "/" + timeStamp + ".pdf");

        //creates the pdf file
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);

        //puts the date to pdf report
        document.open();
        Paragraph paragraph = new Paragraph("Generated: " + new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(date));
        document.add(paragraph);

        //puts the clients info to the pdf report
        addClientDetails(document);

        //puts containers info to the pdf report
        int itemsSum = 0;
        Cursor containers = helper.getClientContainers(client_id);
        for (int i=0;i<containers.getCount();++i){
            containers.moveToPosition(i);
            Container container = new Container(containers);
            itemsSum += addContainer(document, container);
        }

        //puts the containers and items count to the pdf report
        document.add(new Paragraph(getString(R.string.containersqty) + containers.getCount()));
        document.add(new Paragraph(getString(R.string.itemqty) + itemsSum));

        //closes the document
        document.close();
    }

    //adds the info about the container and the item list related to the container and
    //returns the list of items
    private int addContainer(Document document, Container container) throws DocumentException {

        Paragraph paragraph = new Paragraph("\nContainer: "+container.getBarcode());
        paragraph.add(new Paragraph());
        if (container.getLocation1().length()>0)
            paragraph.add("Location "+container.getLocation1());
        if (container.getLocation2().length()>0)
            paragraph.add(", "+container.getLocation2());
        if (container.getComment().length()>0)
            paragraph.add("\nComment: "+container.getComment());
        Cursor items = helper.getContainerItems(container.getId());

        for (int i=0;i<items.getCount();++i){
            items.moveToPosition(i);
            Item item = new Item(items);
            paragraph.add(new Paragraph("\tItem: "+item.getBarcode()));
        }
        document.add(paragraph);

        return items.getCount();
    }

    private void changeLayout(){
        //hides progressbar
        findViewById(R.id.layout_wait).setVisibility(View.GONE);
        //shows display
        findViewById(R.id.layout_send).setVisibility(View.VISIBLE);
        EditText email = (EditText) findViewById(R.id.email);
        //puts the client email address
        email.setText(client.getEmail());
        Button sendBtn = (Button) findViewById(R.id.sent);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends email and closes the activity
                sendMsg();
                finish();
            }
        });
    }

    //adds the info about the client
    private void addClientDetails(Document document) throws DocumentException {
        Client client = helper.getClient(client_id);
        //adds the date of report generation to db
        Date date = new Date() ;
        client.setDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
        helper.updateClient(client);
        //adds client info
        Paragraph paragraph = new Paragraph("Client details\n");
        paragraph.add("Name: "+client.getFirstname() + " " + client.getSurname()+"\n");
        paragraph.add("E-mail: "+client.getEmail()+"\n");
        paragraph.add("Phone: "+client.getPhone()+"\n");
        paragraph.add("Address: "+client.getAddress1());
        if (client.getAddress2().length()>0)
            paragraph.add(", "+client.getAddress2());
        if (client.getAddress3().length()>0)
            paragraph.add(", "+client.getAddress3());
        if (client.getAddress4().length()>0)
            paragraph.add(", "+client.getAddress4());
        if (client.getAddress5().length()>0)
            paragraph.add(", "+client.getAddress5());
        paragraph.add("\n");
        document.add(paragraph);
    }

    //sends email with pdf file
    private void sendMsg(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, "Generated report");
        email.putExtra(Intent.EXTRA_TEXT, "Generated report");
        EditText edittext = (EditText) findViewById(R.id.email);
        String emailStr = edittext.getText().toString().trim();
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailStr });
        Uri uri = Uri.fromFile(pdfFile);
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("message/rfc822");
        startActivity(email);
    }
}
