package com.canteen.canteenapp.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.canteen.canteenapp.Adapters.FoodListFromSellerAdapter;
import com.canteen.canteenapp.Adapters.FoodListSellerOrderAdapter;
import com.canteen.canteenapp.LoginActivity;
import com.canteen.canteenapp.models.BasketDto;
import com.canteen.canteenapp.models.FoodDto;
import com.canteen.canteenapp.models.OrderDto;
import com.canteen.canteenapp.models.RegistrationStudentDTO;
import com.canteen.canteenapp.models.StudentIDOrderForSellerDTO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SellerPrespectiveController implements FoodManagement {

    private DatabaseReference dbReferences;
    private FirebaseDatabase db;

//    private FirebaseStorage storage;
//    private StorageReference storageRef;

    private String nameOfCollege;
    private String uid;
    private String foodCode;

    private List<FoodDto> itemList;
    private List<String> keysFood;

//    private List<StudentIDOrderForSellerDTO> orderFromBasket;
//    private List<OrderDto> orderWithFoodModel;
//    private List<OrderDto> latestOrderComplete;

    private Context context;


    private List<BasketDto> basket ;

    List<OrderDto> po ;


    public SellerPrespectiveController(Context context){

        this.context = context;

        basket = new ArrayList<>();

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        keysFood = new ArrayList<>();
        itemList = new ArrayList<>();
        po = new ArrayList<>();

//        orderFromBasket = new ArrayList<>() ;
//        orderWithFoodModel = new ArrayList<>() ;
//        latestOrderComplete= new ArrayList<>() ;

//        db = FirebaseDatabase.getInstance();
//        dbReferences = db.getReference("/Seller/"+uid+"/college/");
//        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                nameOfCollege = snapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        nameOfCollege = LoginActivity.getDefaults("college", context);




    }


    @Override
    public void addFood(final String category, final String name, final Double price, final String note, Uri filePath, final Context context) {

        foodCode = UUID.randomUUID().toString();


        //uploadPhotos
        if(filePath != null)
        {

            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
//            progressDialog.show();

            final StorageReference ref = FirebaseStorage.getInstance().getReference().child("foodImages/"+nameOfCollege+"/"+foodCode);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                          @Override
                                                                          public void onSuccess(Uri uri) {
                                                                              Uri downloadUrl = uri;
                                                                              dbReferences = db.getReference("/Foods/" + nameOfCollege + "/" + foodCode + "/");
                                                                              dbReferences.setValue(new FoodDto( category,  name, price, note, downloadUrl.toString(), uid , false));
                                                                              //Do what you want with the url

                                                                              dbReferences = db.getReference("/Seller/"+uid+"/Foods/");
                                                                              HashMap<String, Object> foodList = new HashMap<>();
                                                                              foodList.put(foodCode , name);
                                                                              dbReferences.updateChildren(foodList);

                                                                          }
                                                                      });



                            progressDialog.dismiss();
                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }else{
            Toast.makeText(context, "Choose your picture", Toast.LENGTH_SHORT).show();

        }

        //adding to food tree in datbase


        //adding food into the list inside seller tree


    }



    @Override
    public void deleteFood(String key) {
        dbReferences = db.getReference("/Foods/" + nameOfCollege + "/" + key + "/");
        dbReferences.removeValue();

        dbReferences = db.getReference("/Seller/" + uid + "/Foods/"+key);
        dbReferences.removeValue();

    }

    @Override
    public void editFood(String categ, double price, String details, boolean special, String key) {
        dbReferences = db.getReference("/Foods/" + nameOfCollege + "/" + key + "/");
        System.out.println("/Foods/" + nameOfCollege + "/" + key + "/");
        dbReferences.child("price").setValue(price);
        dbReferences.child("details").setValue(details);
        dbReferences.child("special").setValue(special);
        dbReferences.child("category").setValue(categ);



    }

    public void doneCreateMeal(OrderDto order){

        dbReferences = db.getReference("/UniversalOrders/"+order.getBasket().getKey());
        dbReferences.removeValue();

        dbReferences = db.getReference("/OrderReady/"+order.getBasket().getKey()+"/");
        dbReferences.setValue(order.getBasket().getBuyerToken());

        dbReferences = db.getReference("/UniversalOrderReady/"+order.getBasket().getKey());
        dbReferences.setValue(order.getBasket());


        dbReferences = db.getReference("Seller/"+order.getBasket().getSellerUID()+"/Order/"+order.getBasket().getKey());
        dbReferences.setValue("true");

        dbReferences = db.getReference("Student/"+order.getBasket().getBuyerUID()+"/OrderedFood/"+order.getBasket().getKey());
        dbReferences.setValue("true");

    }

    public void fillupRecycleOrder(final RecyclerView view , final Context context , final View viewText){
       lookingBuyer(new populateToRecycle() {
           @Override
           public void onCallBack(List<OrderDto> basketNow) {

               if(basketNow.size() == basket.size()){
                   viewText.setVisibility(View.GONE);
                   view.setAdapter(new FoodListSellerOrderAdapter(basketNow , context));

               }

           }
       });
    }

    private void lookingBuyer(final populateToRecycle call){

        lookingFoodOrders(new lookAtBuyers() {
            @Override
            public void onCallBack(List<OrderDto> basketS) {
                final List<OrderDto> po = new ArrayList<>();

                if( basketS.size() == basket.size()){

                    for(final OrderDto dto : basketS){

                        final OrderDto order = dto;
                        dbReferences = FirebaseDatabase.getInstance().getReference("/Student"+ "/"+dto.getBasket().getBuyerUID());
                        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                RegistrationStudentDTO dto2 = snapshot.getValue(RegistrationStudentDTO.class);
                                order.setStudentModel(dto2);

                                System.out.println(order.getFood().getName());
                                System.out.println("fname is "+snapshot.child("fName").getValue());


                                po.add(order);
                                call.onCallBack(po);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }



            }
        });

    }



    private void lookingFoodOrders(final lookAtBuyers call){

        readOrderTree(new lookAtOrders() {
            @Override
            public void onCallBack(List<BasketDto> basket) {

                System.out.println(basket.size());

                for(final BasketDto dto : basket){
                    dbReferences  = FirebaseDatabase.getInstance().getReference("Foods/"+nameOfCollege+"/"+dto.getFoodID());
                    dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {



//                            FoodDto dto2 = new FoodDto( snapshot.child("name").getValue(String.class),
//                                    snapshot.child("price").getValue(Double.class),
//                                    snapshot.child("details").getValue(String.class),
//                                    snapshot.child("urlpic").getValue(String.class),
//                                    snapshot.child("owner").getValue(String.class),
//                                    false);

                            FoodDto dto2 = snapshot.getValue(FoodDto.class);
                            System.out.println("details : "+snapshot.child("details").getValue(String.class));
                            System.out.println("/Foods/"+
                                    nameOfCollege+"/"+dto.getFoodID());
                            System.out.println("college : "+nameOfCollege);
                            System.out.println(snapshot.getValue(FoodDto.class).getPrice());
                            System.out.println(snapshot.getValue(FoodDto.class).getURLPic());




                            OrderDto dto1 = new OrderDto(dto2);
                            dto1.setBasket(dto);

                            po.add(dto1);
                            call.onCallBack(po);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                        }
                    });
                }

            }
        });


    }


    private void readOrderTree(final lookAtOrders call){

        Query query = FirebaseDatabase.getInstance().getReference("UniversalOrders").orderByChild("sellerUID").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snap : snapshot.getChildren()){

                    GenericTypeIndicator<List<Boolean>> t = new GenericTypeIndicator<List<Boolean>>() {};

                    BasketDto basket1 = new BasketDto( snap.child("buyerUID").getValue(String.class), uid ,snap.child("foodID").getValue(String.class) , snap.child("comment").getValue(String.class)  );
                    basket1.setKey(snap.getKey());
                    basket1.setBuyerToken(snap.child("buyerToken").getValue(String.class));
                    basket1.setOrderKey(snap.child("orderKey").getValue(String.class));
                    basket1.setNoOrder(snap.child("noOrder").getValue(Integer.class));
                    basket1.setTime(snap.child("time").getValue(Long.class));
                    basket1.setAddition(snap.child("addition").getValue(t));

                    basket.add(basket1);

                }
                call.onCallBack(basket);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface lookAtBuyers{
        void onCallBack(List<OrderDto> basket);
    }

    private interface lookAtOrders{
        void onCallBack(List<BasketDto> basket);
    }

    private interface populateToRecycle{
        void onCallBack(List<OrderDto> basket);
    }




    @Override
    public void retriveFoodFromSeller(final RecyclerView recyclerView , final FragmentActivity activity , final View c) {
        readEachKeys(new FirebaseCallbackForEachKey() {
            @Override
            public void onCallBack(List<FoodDto> fd) {
                if( fd.size() == keysFood.size()){
                    c.setVisibility(View.GONE);
                    recyclerView.setAdapter(new FoodListFromSellerAdapter( activity , fd));
                }
            }
        });

    }

    private void readEachKeys(final FirebaseCallbackForEachKey callback){
        readFoodKeysFromSeller(new FirebaseCallback1() {
            @Override
            public void onCallBack(List<String> st) {



                for (String key : st){
                    dbReferences = FirebaseDatabase.getInstance().getReference("/Foods/"+
                            nameOfCollege+"/"+key);
                    dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            FoodDto food = new FoodDto(
                                    snapshot.child("category").getValue(String.class),
                                    snapshot.child("name").getValue(String.class)
                                    , snapshot.child("price").getValue(Double.class)
                                    , snapshot.child("details").getValue(String.class)
                                    , snapshot.child("urlpic").getValue(String.class)
                                    , snapshot.child("owner").getValue(String.class)
                                    ,snapshot.child("special").getValue(Boolean.class));
                            food.setKey(snapshot.getKey());
                            itemList.add(food);
                            callback.onCallBack(itemList);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

//                System.out.println(itemList.toString());




            }
        });
    }

    private void readFoodKeysFromSeller(final FirebaseCallback1 callback){
        dbReferences = FirebaseDatabase.getInstance().getReference("/Seller/"+uid+"/Foods/");
        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot postSnapshot : snapshot.getChildren()) {
                    keysFood.add(postSnapshot.getKey());
                }
                callback.onCallBack(keysFood);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface CallbackToFillUpRecycle{
        void onCallBack(List <OrderDto> order);
    }

    private interface CallBackAfterGetFoodModelRetrived{
        void onCallBack(List <OrderDto> order);
    }

    private interface CallbakAfterRetriveOrder{
       void onCallBack(List<StudentIDOrderForSellerDTO> ds);
    }

    private interface FirebaseCallbackForEachKey {
        void onCallBack(List<FoodDto> fd);
    }

    private interface FirebaseCallback1 {
        void onCallBack(List<String> st);
    }


    @Override
    public List<FoodDto> retriveFoodFromCollege() {
        return null;
    }
}
