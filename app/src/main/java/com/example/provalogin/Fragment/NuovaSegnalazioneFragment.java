package com.example.provalogin.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.HomeVeterinarioActivity;
import com.example.provalogin.Model.Segnalazioni;
import com.example.provalogin.Model.Utente;
import com.example.provalogin.R;
import com.example.provalogin.RegisterActivity;
import com.example.provalogin.UpdateActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;


public class NuovaSegnalazioneFragment extends Fragment {

    Context context;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    FirebaseAuth auth;
    DatabaseReference reference;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String userid;
    FirebaseDatabase db;
    Utente utente;
    Segnalazioni hashMap;

    String id;
    Uri filePathSegnalazione;
    String imgPosition = "gs://provalogin-65cb5.appspot.com/logo.png";

    EditText descrizione, posizione;
    Spinner tipologia;
    Button btnInviaSegnalazione;
    CheckBox cbVeterinario, cbEnte, cbUtenteTradizionale;
    ImageView imgSegnalazione;
    FloatingActionButton caricaImagineProfilo;



    public NuovaSegnalazioneFragment() {
        // Required empty public constructor

    }

    public NuovaSegnalazioneFragment(Utente utentetmp) {
        this.utente=utentetmp;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();
        id = NewAnimal.generacodiceid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference().child("Segnalazioni").child(id);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_nuova_segnalazione, container, false);



    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        descrizione=view.findViewById(R.id.txt_descrizione);
        posizione=view.findViewById(R.id.txt_posizione);
        tipologia=view.findViewById(R.id.spinner_tipologia);
        btnInviaSegnalazione=view.findViewById(R.id.btn_invia_nuova_segnalazione);
        cbEnte=view.findViewById(R.id.checkbox_ente);
        cbUtenteTradizionale=view.findViewById(R.id.checkbox_utentetradizionale);
        cbVeterinario=view.findViewById(R.id.checkbox_veterinario);
        imgSegnalazione=view.findViewById(R.id.img_segnalazione);
        caricaImagineProfilo= view.findViewById(R.id.btn_nuava_foto_segnalazione);
        hashMap = new Segnalazioni();
        hashMap.imgSegnalazione = imgPosition;
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(hashMap.imgSegnalazione);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(imgSegnalazione);
            }
        });



        btnInviaSegnalazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd =new ProgressDialog(view.getContext());
                pd.setMessage("Caricamento...");
                pd.show();
                String destinatarioEnte="no";
                String destinatarioVeterinario="no";
                String destinatarioUtente="no";
                if(cbVeterinario.isChecked()){
                    destinatarioVeterinario="si";
                }
                if(cbEnte.isChecked()){
                    destinatarioEnte="si";
                }
                if(cbUtenteTradizionale.isChecked()){
                    destinatarioUtente="si";
                }


                hashMap.id=id;
                hashMap.tipologiaSegnalazione =  tipologia.getSelectedItem().toString();
                hashMap.descrizione = descrizione.getText().toString();
                hashMap.posizione = posizione.getText().toString();
                hashMap.destinatarioVeterionario = destinatarioVeterinario;
                hashMap.destinatarioEnte = destinatarioEnte;
                hashMap.destinatarioUtente = destinatarioUtente;
                hashMap.idMittente = userid;
                hashMap.presaInCarico = "no";
                hashMap.idPresaInCarico = "no";
                hashMap.imgSegnalazione = imgPosition;

                /*
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", id);
                hashMap.put("tipologiaSegnalazione", tipologia.getSelectedItem().toString());
                hashMap.put("descrizione", descrizione.getText().toString());
                hashMap.put("posizione", posizione.getText().toString());
                hashMap.put("destinatarioVeterionario", destinatarioVeterinario);
                hashMap.put("destinatarioEnte", destinatarioEnte);
                hashMap.put("destinatarioUtente", destinatarioUtente);
                hashMap.put("idMittente",userid);
                hashMap.put("presoInCarico","no");
                hashMap.put("idPresoInCarico", "no");
                hashMap.put("imgSegnalazione",imgSegnalazione.toString());
                 */

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                                switch (utente.TipoUtente){
                                    case "EntePubblico":
                                        startActivity(new Intent(view.getContext(), HomeEnteActivity.class));
                                        break;
                                    case "Utente Amico":
                                        startActivity(new Intent(view.getContext(), HomeActivity.class));
                                        break;
                                    case "Veterinario":
                                        startActivity(new Intent(view.getContext(), HomeVeterinarioActivity.class));
                                        break;
                                }
                    }
                });
                pd.dismiss();
            }
        });

        caricaImagineProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( context.checkSelfPermission( Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED &&
                        context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permission = { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission, PERMISSION_CODE);
                }
                else{
                    openCamera();
                }

            }

        });






    }

    public void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        filePathSegnalazione = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, filePathSegnalazione);
        startActivityForResult( cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CAPTURE_CODE){
            imgSegnalazione.setImageURI(filePathSegnalazione);
            uploadImage();
        }


    }


    private void uploadImage() {
        if (filePathSegnalazione != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            String random = UUID.randomUUID().toString();
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + random);

            imgPosition = "gs://provalogin-65cb5.appspot.com/images/" + random;


            // adding listeners on upload
            // or failure of image
            ref.putFile(filePathSegnalazione)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(context,
                                                    R.string.immagine_caricata,
                                                    Toast.LENGTH_SHORT)
                                            .show();

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(context,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }

}