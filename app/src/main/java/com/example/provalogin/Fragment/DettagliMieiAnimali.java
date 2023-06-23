package com.example.provalogin.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.provalogin.Adapter.ImageAdapter;
import com.example.provalogin.HomeActivity;
import com.example.provalogin.HomeEnteActivity;
import com.example.provalogin.HomeVeterinarioActivity;
import com.example.provalogin.Model.Animal;
import com.example.provalogin.Model.Image;
import com.example.provalogin.Model.Spesa;
import com.example.provalogin.R;
import com.example.provalogin.UpdateActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DettagliMieiAnimali extends Fragment {

    AlertDialog dialog;
    Bitmap bitmap = null;
    Animal animale;
    String position = new String();
    ImageView imgProfilo;
    FloatingActionButton btnNuovaFotoProfilo, btnAlbumFoto, btnCodividiQrcode;
    Button btnSpese, btnSalute, btnCancella;
    TextView txtNomeAnimale;

    RecyclerView recyclerView;
    private List<Image> mImage;
    private ImageAdapter imageAdapter;
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mImage.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Image segnalazioni = snapshot.getValue(Image.class);
                    mImage.add(segnalazioni);
                }
                imageAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public DettagliMieiAnimali(Animal tmp) {
        this.animale=tmp;
    }
    public DettagliMieiAnimali(Animal tmp, String position) {
        this.animale=tmp;
        this.position=position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_animal, container, false);
        Activity activity = getActivity();
        if (activity instanceof HomeVeterinarioActivity) {
            ((HomeVeterinarioActivity) activity).setCustomBackEnabled(false);
        } else if (activity instanceof HomeEnteActivity) {
            ((HomeEnteActivity) activity).setCustomBackEnabled(false);
        }else if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setCustomBackEnabled(false);
        }
        imgProfilo = view.findViewById(R.id.profileImg);
        btnNuovaFotoProfilo = view.findViewById(R.id.btn_nuava_foto_mio_animale);
        btnAlbumFoto = view.findViewById(R.id.btn_nuava_foto_album_foto);
        txtNomeAnimale = view.findViewById(R.id.name_mio_animale);
        btnSpese = view.findViewById(R.id.editButtonspese);
        btnSalute = view.findViewById(R.id.editButtonsalute);
        btnCodividiQrcode = view.findViewById(R.id.btn_codividiQrCode);
        btnCancella = view.findViewById(R.id.btn_cancella_animale);
        btnSalute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new SaluteAnimaliFragment(animale))
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnSpese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SpeseAnimaleFragment(animale)).addToBackStack(null).commit();
            }
        });

        btnCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new androidx.appcompat.app.AlertDialog.Builder(btnCancella.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("ELIMINAZIONE ANIMALE").setMessage("Sei sicuro di voler eliminare?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference reference;
                                FirebaseDatabase database = FirebaseDatabase.getInstance("https://provalogin-65cb5-default-rtdb.europe-west1.firebasedatabase.app/");
                                reference = database.getReference().child("Animals").child(animale.id);
                                reference.removeValue();
                                getActivity().getSupportFragmentManager().popBackStack();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        if(!(position.isEmpty()) && position.equals("rendiInvisibiliBottoni")){
            rendiInvisibiliBottoni();
        }

        //LOGICA SEGNALAZIONI ADAPTER
        recyclerView = view.findViewById(R.id.recycler_view_foto_album_animali);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(this.getContext(), mImage);

        recyclerView.setAdapter(imageAdapter);

        Query db= FirebaseDatabase.getInstance().getReference("Image").orderByChild("idAnimale").equalTo(animale.id);
        db.addValueEventListener(valueEventListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        caricaImgProfilo();



        txtNomeAnimale.setText(animale.nomeAnimale);



        btnNuovaFotoProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("Animale", animale);
                intent.putExtra("Posizione", "dettagliMieiAnimali");
                startActivityForResult(intent, 0111);
            }
        });

        btnAlbumFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("Animale", animale);
                intent.putExtra("Posizione", "nuovaFotoAlbum");
                startActivity(intent);
            }
        });




        btnCodividiQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Animals").child(animale.id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                animale = task.getResult().getValue(Animal.class);
                caricaImgProfilo();

            }

        });
    }

    public void caricaImgProfilo(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(animale.imgAnimale);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(imgProfilo.getContext()).load(uri).into(imgProfilo);
            }
        });
    }

    private void rendiInvisibiliBottoni(){
        btnAlbumFoto.setVisibility(View.INVISIBLE);
        btnNuovaFotoProfilo.setVisibility(View.INVISIBLE);
        btnSpese.setVisibility(View.INVISIBLE);
        btnSalute.setVisibility(View.INVISIBLE);
        btnCancella.setVisibility(View.INVISIBLE);
        btnCodividiQrcode.setVisibility(View.INVISIBLE);
    }

    private ImageView imgQrCode;
    private void buildDialog() {
        //alertdialog
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.fragment_q_r_c_o_d_e,null);
        imgQrCode = view.findViewById(R.id.img_qrcode);
        builder.setView(view);
        generateQR();
        dialog = builder.create();
        dialog.show();
*/
        //fragment basso
        final Dialog dialog2 = new Dialog(getContext());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.fragment_q_r_c_o_d_e);

        ImageView imgQrCode2 = dialog2.findViewById(R.id.img_qrcode);
        FloatingActionButton btnCondividi = dialog2.findViewById(R.id.btn_codividi);

        String text = animale.id;
        MultiFormatWriter writer = new MultiFormatWriter();

        try
        {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,600,600);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
            imgQrCode2.setImageBitmap(bitmap);

        } catch (WriterException e)
        {
            e.printStackTrace();
        }

        dialog2.show();
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog2.getWindow().setGravity(Gravity.BOTTOM);


        btnCondividi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImageandText(bitmap);
            }
        });

    }

    private void shareImageandText(Bitmap bitmap) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "Ecco QRCODE del mio Animale, scansionalo!!\n DATI PUBBLICI ANIMALI:\n\nnome: "+animale.nomeAnimale+"\nspecie: "+animale.specie+"\nsesso: "+animale.sesso+"\netà: "+animale.eta);

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

        // setting type to image
        intent.setType("image/png");

        // calling startactivity() to share
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getActivity().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(getContext(),"com.example.provalogin", file);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Condivisione Fallita!!" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    private void generateQR()
    {
        String text = animale.id;
        MultiFormatWriter writer = new MultiFormatWriter();
        try
        {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,600,600);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            imgQrCode.setImageBitmap(bitmap);

        } catch (WriterException e)
        {
            e.printStackTrace();
        }
    }

}
