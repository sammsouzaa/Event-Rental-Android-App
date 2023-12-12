package com.example.lazerrenttest.Database;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Information_getImovel implements Serializable {
    private String status;
    private String typesofproperties, title, description, propertysize, price, capacity, beds, baths, street, streetnumber, neighborhood, city, ID_imoveis, idProprietario, nFavoritos, nLocations;

    private String ID;
    private Uri photoPrincipal, photo1, photo2, photo3, photo4;

    private Context context;

    public Information_getImovel(Context context, String ID){
        this.ID = ID;
        this.context = context;
    }

    public interface ImovelInfoCallback {
        void onImovelInfoLoaded();
        void onImovelInfoFailed(Exception e);
    }

    public void fetchImovelInfo(ImovelInfoCallback callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("imovel").document(ID);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    status = document.getString("status");
                    typesofproperties = document.getString("typesofproperties");
                    title = document.getString("title");
                    description = document.getString("description");
                    propertysize = document.getString("propertysize");
                    price = document.getString("price");
                    capacity = document.getString("capacity");
                    beds = document.getString("beds");
                    baths = document.getString("baths");
                    street = document.getString("street");
                    streetnumber = document.getString("streetnumber");
                    neighborhood = document.getString("neighborhood");
                    city = document.getString("city");
                    ID_imoveis = document.getString("id_imoveis");
                    idProprietario = document.getString("idProprietario");
                    nFavoritos = document.getString("nFavoritos");
                    nLocations = document.getString("nLocations");

                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                    String folderPath = "images/imovel/" + ID_imoveis + "/";

                    List<String> imageNames = Arrays.asList("Photo Principal", "Photo 1", "Photo 2", "Photo 3", "Photo 4");

                    List<Task<Uri>> downloadTasks = new ArrayList<>();

                    for (String imageName : imageNames) {
                        StorageReference imageRef = storageRef.child(folderPath + imageName);
                        Task<Uri> task2 = imageRef.getDownloadUrl();
                        downloadTasks.add(task2);
                    }

                    Tasks.whenAllComplete(downloadTasks).addOnCompleteListener(task2 -> {
                        if (task.isSuccessful()) {
                            List<Uri> imageUris = new ArrayList<>();

                            for (Task<Uri> downloadTask : downloadTasks) {
                                if (downloadTask.isSuccessful()) {
                                    Uri uri = downloadTask.getResult();
                                    imageUris.add(uri);
                                }
                            }
                            if (!imageUris.isEmpty()) {
                                photoPrincipal = imageUris.get(0);
                                photo1 = imageUris.get(1);
                                photo2 = imageUris.get(2);
                                photo3 = imageUris.get(3);
                                photo4 = imageUris.get(4);
                            }
                            callback.onImovelInfoLoaded();
                        } else {
                            // Lidar com erro na obtenção das URLs de download
                            callback.onImovelInfoFailed(new Exception("Erro ao carregar informações do imóvel"));
                        }
                    });

                } else {
                    // Documento não encontrado
                }
            } else {
                // Falha ao obter o documento
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getStatus() {
        return status;
    }

    public String getTypesofproperties() {
        return typesofproperties;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPropertysize() {
        return propertysize;
    }

    public String getPrice() {
        return price;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getBeds() {
        return beds;
    }

    public String getBaths() {
        return baths;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getID_imoveis() {
        return ID_imoveis;
    }

    public String getIdProprietario() {
        return idProprietario;
    }

    public Uri getPhotoPrincipal() {
        return photoPrincipal;
    }

    public Uri getPhoto1() {
        return photo1;
    }

    public Uri getPhoto2() {
        return photo2;
    }

    public Uri getPhoto3() {
        return photo3;
    }

    public Uri getPhoto4() {
        return photo4;
    }

    public String getnFavoritos() {
        return nFavoritos;
    }

    public String getnLocations() {
        return nLocations;
    }
}
