package com.canteen.canteenapp.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.canteen.canteenapp.Adapters.FoodListStudentCheckOutAdapter;
import com.canteen.canteenapp.Adapters.FoodListStudentHomeAdapter;
import com.canteen.canteenapp.Adapters.FoodListStudentInvoiceAdapter;
import com.canteen.canteenapp.Adapters.FoodListStudentOrderReadyNProcessAdapter;
import com.canteen.canteenapp.LoginActivity;
import com.canteen.canteenapp.models.BasketDto;
import com.canteen.canteenapp.models.FoodDto;
import com.canteen.canteenapp.models.FoodWithNumberOrderInvoice;
import com.canteen.canteenapp.models.Invoice;
import com.canteen.canteenapp.models.InvoiceWithListOfFoodDto;
import com.canteen.canteenapp.models.UnpaidBasketDto;
import com.canteen.canteenapp.models.checkOutBasket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class StudentPrespectiveController {

    private String uid;
    private FoodListStudentHomeAdapter adapter;
    private List<FoodDto> itemList;
    private DatabaseReference dbReferences;
    private String collegeName;
    private HashMap<String, String> foodsBasket;
    private List<String> tokenSeller;
    private Context context;
    private String token;
    private String keyIDForOrder;

    private List<UnpaidBasketDto> foodAtBasket;
    private List<Map<String, Object>> foodAtOrders;
    private List<Map<String, Object>> foodAtOrdersComplete;

    private List<checkOutBasket> foodAtBasketInModel;
    private List<checkOutBasket> foodAtOrdersInModel;
    private List<checkOutBasket> foodAtOrdersInModelComplete;


    private List<BasketDto> basketIDCollected;
    private List<BasketDto> basketIDCollectedwithToken;

    private List<FoodDto> fooosds;

    private Double totalPriceAtBasket;

    private double totalPrice;

    private List<Invoice> invoices;
    private List<InvoiceWithListOfFoodDto> invoiceswithFoodDto;


    public StudentPrespectiveController(Context context) {

        this.context = context;

        foodAtOrdersInModel = new ArrayList<>();
        foodAtOrders = new ArrayList<>();

        foodAtOrdersInModelComplete = new ArrayList<>();
        foodAtOrdersComplete = new ArrayList<>();

        foodAtBasket = new ArrayList<>();
        foodAtBasketInModel = new ArrayList<>();

        invoices = new ArrayList<>();
        invoiceswithFoodDto = new ArrayList<>();


        collegeName = LoginActivity.getDefaults("college", context);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        itemList = new ArrayList<>();
        tokenSeller = new ArrayList<>();
        foodsBasket = new HashMap<String, String>();
        token = LoginActivity.getDefaults("token", context);
        basketIDCollected = new ArrayList<BasketDto>();
        basketIDCollectedwithToken = new ArrayList<BasketDto>();


    }

    public void populateRecyleViewInvoice(final RecyclerView invoice, final TextView invoiceText){
        populateinvoicewithFoods(new CallToPopulataRecycle() {
            @Override
            public void onCallback(List<InvoiceWithListOfFoodDto> invoicesa) {
                if(invoicesa.size() == invoices.size()){

                    invoice.setAdapter(new FoodListStudentInvoiceAdapter(context , invoicesa));
                    invoiceText.setVisibility(View.GONE);
                }

            }
        });
    }



    private void populateinvoicewithFoods(final CallToPopulataRecycle call) {
        gettingInvoice(new CallToPopulateFodd() {
            @Override
            public void onCallback(List<Invoice> invoice) {
                for (final Invoice voice : invoice) {

                    final Map<String ,Object> food = voice.getFoodID();
                    final List<FoodWithNumberOrderInvoice> foodss = new ArrayList<FoodWithNumberOrderInvoice>();

                        Query query = FirebaseDatabase.getInstance().getReference("Foods/"+collegeName+"/");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (Map.Entry<String, Object> set : food.entrySet()) {

                                    int numberOfOrder = Integer.parseInt(String.valueOf(set.getValue()));

                                    foodss.add(new FoodWithNumberOrderInvoice(snapshot.child(set.getKey()).getValue(FoodDto.class)  , numberOfOrder ));
                                }

                                InvoiceWithListOfFoodDto p = new InvoiceWithListOfFoodDto(voice , foodss);
                                invoiceswithFoodDto.add(p);
                                call.onCallback(invoiceswithFoodDto);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                }
            }
        });
    }


    private interface CallToPopulataRecycle {
        void onCallback(List<InvoiceWithListOfFoodDto> invoice);
    }



    private void gettingInvoice(final CallToPopulateFodd call) {


        Query query = FirebaseDatabase.getInstance().getReference("UniversalInvoice/").orderByChild("uidbuyer").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {

                    Invoice invoice = snap.getValue(Invoice.class);
                    invoices.add(invoice);

                }

                call.onCallback(invoices);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface CallToPopulateFodd {
        void onCallback(List<Invoice> invoice);
    }


    public void fillUpRecycleCompleteProccess(final RecyclerView view, final View viewText) {

        gettingFoodModelFoCompleteProccess(new RetriveItToRecycle() {
            @Override
            public void onCallBack(List<checkOutBasket> foods) {

                if (foods.size() == foodAtOrdersComplete.size()) {

//                    for(checkOutBasket d : foods){
//                        System.out.println(d.getFood().getName());
//                        System.out.println(d.getFood().getURLPic());
//

                    view.setAdapter(new FoodListStudentOrderReadyNProcessAdapter(context, foods));
                    viewText.setVisibility(View.GONE);


                }


            }

        });

    }


    private void gettingFoodModelFoCompleteProccess(final RetriveItToRecycle call) {

        lookingAtBasketForCompleteProccess(new foodFromUniversalBasket() {
            @Override
            public void onCallBack(List<Map<String, Object>> foodID) {

                for (final Map<String, Object> food : foodID) {

                    Query query = FirebaseDatabase.getInstance().getReference("Foods/" + collegeName + "/" + food.get("foodKey"));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            checkOutBasket bask = new checkOutBasket(snapshot.getValue(FoodDto.class), food.get("basketKey").toString());

                            bask.setBasketKeyDif(food.get("orderKey").toString());
                            bask.setNoorder((Integer) food.get("noOrder"));

                            foodAtOrdersInModelComplete.add(bask);
                            call.onCallBack(foodAtOrdersInModelComplete);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private void lookingAtBasketForCompleteProccess(final foodFromUniversalBasket call) {

        Query query = FirebaseDatabase.getInstance().getReference("UniversalOrderReady").orderByChild("buyerUID").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Map<String, Object>> op = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {

                    Map<String, Object> po = new HashMap<String, Object>();

                    po.put("foodKey", snap.child("foodID").getValue(String.class));
                    po.put("basketKey", snap.getKey());


                    po.put( "orderKey" , snap.child("orderKey").getValue(String.class));
                    po.put( "noOrder" , snap.child("noOrder").getValue(int.class));

                    op.add(po);

                }
                foodAtOrdersComplete = op;
                call.onCallBack(foodAtOrdersComplete);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void fillUpRecycleOrderProccess(final RecyclerView view, final View viewText) {

        gettingFoodModelForOrderProccess(new RetriveItToRecycle() {
            @Override
            public void onCallBack(List<checkOutBasket> foods) {

                if (foods.size() == foodAtOrders.size()) {

//                    for(checkOutBasket d : foods){
//                        System.out.println(d.getFood().getName());
//                        System.out.println(d.getFood().getURLPic());
//

                    view.setAdapter(new FoodListStudentOrderReadyNProcessAdapter(context, foods));
                    viewText.setVisibility(View.GONE);


                }


            }

        });

    }


    private void gettingFoodModelForOrderProccess(final RetriveItToRecycle call) {

        lookingAtBasketForOrderProccess(new foodFromUniversalBasket() {
            @Override
            public void onCallBack(List<Map<String, Object>> foodID) {

                for (final Map<String, Object> food : foodID) {

                    Query query = FirebaseDatabase.getInstance().getReference("Foods/" + collegeName + "/" + food.get("foodKey"));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            checkOutBasket bask = new checkOutBasket(snapshot.getValue(FoodDto.class), food.get("basketKey").toString());
                            bask.setBasketKeyDif(food.get("orderKey").toString());
                            bask.setNoorder((Integer) food.get("noOrder"));

                            foodAtOrdersInModel.add(bask);
                            call.onCallBack(foodAtOrdersInModel);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private void lookingAtBasketForOrderProccess(final foodFromUniversalBasket call) {

        Query query = FirebaseDatabase.getInstance().getReference("UniversalOrders").orderByChild("buyerUID").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Map<String, Object>> op = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {

                    Map<String, Object> po = new HashMap<String, Object>();

                    po.put("foodKey", snap.child("foodID").getValue(String.class));
                    po.put("basketKey", snap.getKey());

                    po.put( "orderKey" , snap.child("orderKey").getValue(String.class));
                    po.put( "noOrder" , snap.child("noOrder").getValue(int.class));

                    op.add(po);

                }
                foodAtOrders = op;
                call.onCallBack(foodAtOrders);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void pushNofiticationToSeller(final Activity act, final int checkedItem) {


        writeToSellerAndGetToken(new messageSeller() {
            @Override
            public void onCallBack(List<BasketDto> basketWithToken) {

                if (basketWithToken.size() == basketIDCollected.size()) {

                    Map<String,Object> codeFood = new HashMap<String , Object>();

                    String orderKeybasedOnBuyer = UUID.randomUUID().toString();
                    List<String> orderKey = new ArrayList<String>();


                    for (BasketDto dto : basketWithToken) {
                        codeFood.put(dto.getFoodID() , dto.getNoOrder());
                        orderKey.add(dto.getKey());

                        dto.setOrderKey(orderKeybasedOnBuyer);
                        dto.setTime(new Date().getTime());

                        dbReferences = FirebaseDatabase.getInstance().getReference("OrdersLine/");
                        dbReferences.child(dto.getKey()).setValue(dto.getSellerToken());
                        dbReferences = FirebaseDatabase.getInstance().getReference("UniversalOrders/");
                        dbReferences.child(dto.getKey()).setValue(dto);

                        dbReferences = FirebaseDatabase.getInstance().getReference("Student/"+uid+"/Basket");
                        dbReferences.removeValue();

                    }


                    Invoice invoice = new Invoice(uid, codeFood , totalPriceAtBasket, new Date().getTime(), checkedItem , orderKeybasedOnBuyer);


                    dbReferences = FirebaseDatabase.getInstance().getReference("UniversalInvoice/");
                    dbReferences.child(UUID.randomUUID().toString()).setValue(invoice);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle( "Payment Successful" )
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                }
                            }).show();


                }

            }
        });

    }

    public void writeToSellerAndGetToken(final messageSeller call) {
        payMechaninsm(new NotifyTheSeller() {
            @Override
            public void onCallBack(List<BasketDto> basketId) {
                for (BasketDto dto : basketId) {
                    dbReferences = FirebaseDatabase.getInstance().getReference("Seller/" + dto.getSellerUID());
                    dbReferences.child("Order").child(dto.getKey()).setValue(false);
                    dbReferences = FirebaseDatabase.getInstance().getReference("Student/" + dto.getBuyerUID());
                    dbReferences.child("OrderedFood").child(dto.getKey()).setValue(false);
                }

                for (final BasketDto dto : basketId) {

                    final BasketDto dto2 = dto;

                    dbReferences = FirebaseDatabase.getInstance().getReference("Seller/" + dto.getSellerUID());
                    dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            dto2.setSellerToken(snapshot.child("token").getValue(String.class));
                            basketIDCollectedwithToken.add(dto);


                            call.onCallBack(basketIDCollectedwithToken);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }
        });
    }


    private void payMechaninsm(final StudentPrespectiveController.NotifyTheSeller call) {


        Query query = FirebaseDatabase.getInstance().getReference("Student/"+uid+"/Basket/");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {

                    GenericTypeIndicator<List<Boolean>> t = new GenericTypeIndicator<List<Boolean>>() {};
                    BasketDto dto = new BasketDto(snap.child("buyer").getValue(String.class), snap.child("owner").getValue(String.class), snap.child("foodKey").getValue(String.class), snap.child("comment").getValue(String.class));
                    dto.setNoOrder(snap.child("noorder").getValue(int.class));
                    dto.setBuyerToken(snap.child("buyerToken").getValue(String.class));
                    dto.setKey(UUID.randomUUID().toString());
                    dto.setAddition(snap.child("addition").getValue(t));


                    basketIDCollected.add(dto);

                }
                call.onCallBack(basketIDCollected);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void addingToBasket(final String foodKey, final String owner, final int value, final String comment, boolean checkedItems [] ) {

        System.out.println(checkedItems.toString());

        final List<Boolean> additionForRiceSets = new ArrayList<>(checkedItems.length);

        for ( boolean s : checkedItems){
            additionForRiceSets.add(s);
        }
//        Collections.addAll(additionForRiceSets, Boolean.valueOf(checkedItems);

        dbReferences = FirebaseDatabase.getInstance().getReference("Student/"+uid+"/Basket/");
        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(foodKey).exists()){
                    Toast.makeText(context, "You Already Added This Food !", Toast.LENGTH_LONG).show();
                }else{

                    DatabaseReference po = FirebaseDatabase.getInstance().getReference("Student/"+uid+"/Basket/"+foodKey);
                    UnpaidBasketDto bask = new UnpaidBasketDto(comment , owner , foodKey , uid , token , value , additionForRiceSets);
                    po.setValue(bask);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void retriveFoodFromSellerDrink(final RecyclerView recyclerView, final FragmentActivity activity, final View vi ) {

        readEachKeys(new FirebaseCallback1() {
            @Override
            public void onCallBack(List<FoodDto> st) {
                if (st.size() == itemList.size()) {

                    List<FoodDto> Special = new ArrayList<>();

                    for(FoodDto dt : st){
                        if (dt.getCategory().equals("Drinks")){
                            Special.add(dt);
                        }
                    }

                    final FoodListStudentHomeAdapter adapter = new FoodListStudentHomeAdapter(activity, Special);
                    vi.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);


                }


            }
        });

    }

    public void retriveFoodFromSellerRoti(final RecyclerView recyclerView, final FragmentActivity activity, final View vi ) {

        readEachKeys(new FirebaseCallback1() {
            @Override
            public void onCallBack(List<FoodDto> st) {
                if (st.size() == itemList.size()) {
                    List<FoodDto> Roti = new ArrayList<>();

                    for(FoodDto dt : st){
                        if (dt.getCategory().equals("Roti Sets")){
                            Roti.add(dt);
                        }
                    }


                    final FoodListStudentHomeAdapter adapter = new FoodListStudentHomeAdapter(activity, Roti);
                    vi.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);


                }


            }
        });

    }

    public void retriveFoodFromSellerNasi(final RecyclerView recyclerView, final FragmentActivity activity, final View vi ) {

        readEachKeys(new FirebaseCallback1() {
            @Override
            public void onCallBack(List<FoodDto> st) {
                if (st.size() == itemList.size()) {

                    List<FoodDto> Rice = new ArrayList<>();

                    for(FoodDto dt : st){
                        if (dt.getCategory().equals("Rice Sets")){
                            Rice.add(dt);
                        }
                    }

                    final FoodListStudentHomeAdapter adapter = new FoodListStudentHomeAdapter(activity, Rice);
                    vi.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);


                }


            }
        });

    }

    public void retriveFoodFromSellerSpecial(final RecyclerView recyclerView, final FragmentActivity activity, final View vi ) {

        readEachKeys(new FirebaseCallback1() {
            @Override
            public void onCallBack(List<FoodDto> st) {
                if (st.size() == itemList.size()) {

                    List<FoodDto> Special = new ArrayList<>();

                    for(FoodDto dt : st){
                        if (dt.getCategory().equals("Special")){
                            Special.add(dt);
                        }
                    }

                    final FoodListStudentHomeAdapter adapter = new FoodListStudentHomeAdapter(activity, Special);
                    vi.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);


                }


            }
        });

    }

    public void retriveFoodFromSeller(final RecyclerView recyclerView, final FragmentActivity activity, final View vi, final SearchView search, final Switch spc) {

        readEachKeys(new FirebaseCallback1() {
            @Override
            public void onCallBack(List<FoodDto> st) {
                if (st.size() == itemList.size()) {
                    final FoodListStudentHomeAdapter adapter = new FoodListStudentHomeAdapter(activity, itemList);
                    vi.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                    search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            if (s.isEmpty()) {
                                adapter.notifyDataSetChanged();
                            } else {
                                adapter.getFilter().filter(s);
                            }
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {

                            if (s.isEmpty()) {
                                adapter.notifyDataSetChanged();
                            } else {
                                adapter.getFilter().filter(s);
                            }
                            return false;
                        }
                    });

                    spc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (spc.isChecked()) {
                                adapter.turnSpecialOn();
                            } else {
                                adapter.turnSpecialOff();
                            }
                        }
                    });


                }


            }
        });

    }


    private void readEachKeys(final StudentPrespectiveController.FirebaseCallback1 callback) {

        System.out.println("/Foods/" +
                collegeName);

        dbReferences = FirebaseDatabase.getInstance().getReference("/Foods/" +
                collegeName);
        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {

                    if( !(snap.getKey().equals("oprClosing") | snap.getKey().equals("oprOpening"))){
                        FoodDto food = new FoodDto(
                                snap.child("category").getValue(String.class)
                                , snap.child("name").getValue(String.class)
                                , snap.child("price").getValue(Double.class)
                                , snap.child("details").getValue(String.class)
                                , snap.child("urlpic").getValue(String.class)
                                , snap.child("owner").getValue(String.class)
                                , snap.child("special").getValue(Boolean.class));
                        food.setKey(snap.getKey());
                        itemList.add(food);
                    }

                }


                callback.onCallBack(itemList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void deleteItemInBasket(String key) {
        dbReferences = FirebaseDatabase.getInstance().getReference("/Student/" + uid + "/Basket/"+key);
        dbReferences.removeValue();

    }

    public void saveChangeBasket(String basketKey, int value, String s, boolean[] checkedItems) {

        final List<Boolean> additionForRiceSets = new ArrayList<>(checkedItems.length);

        for ( boolean p : checkedItems){
            additionForRiceSets.add(p);
        }
        dbReferences = FirebaseDatabase.getInstance().getReference("/Student/" + uid + "/Basket/"+basketKey);
        dbReferences.child("noorder").setValue(value);
        dbReferences.child("comment").setValue(s);
        dbReferences.child("addition").setValue(additionForRiceSets);



    }



    public void fillUpBasketRecycle(final RecyclerView view, final View viewText, final TextView totalice) {

        gettingFoodModel(new RetriveItToRecycle() {
            @Override
            public void onCallBack(List<checkOutBasket> foods) {

                if (foods.size() == foodAtBasket.size()) {
                    DecimalFormat precision = new DecimalFormat("0.00");

                    double total = 0.0;
                    for (checkOutBasket po : foods) {
                        System.out.println(totalPrice);
                        total += po.getFood().getPrice()*po.getUnpaidBasketDto().getNoorder();
                        List<Boolean> op = po.getUnpaidBasketDto().getAddition();
                        for(int c = 0 ; c<op.size() ; c++){
                            switch (c) {
                                case 1:
                                    if(op.get(1)) total += 2.5;
                                    break;
                                case 2:
                                    if(op.get(2)) total += 2.5;
                                    break;
                                case 3:
                                    if(op.get(3)) total += 2.5;
                                    break;
                                case 4:
                                    if(op.get(5)) total += 2.0;
                                    break;
                                case 5:
                                    if(op.get(5)) total += 1;
                                    break;
                                case 6:
                                    if(op.get(6)) total += 1.2;
                                    break;
                            }
                        }

                        totalice.setText("RM " + precision.format(total));

                    }

                    totalPriceAtBasket = total;


                    view.setAdapter(new FoodListStudentCheckOutAdapter(context, foods));
                    viewText.setVisibility(View.GONE);


                }


            }

        });


    }


    private void gettingFoodModel(final RetriveItToRecycle call) {

        lookingAtBasket(new FirebaseCallbackForBasket1() {
            @Override
            public void onCallBack(List<UnpaidBasketDto> foodID) {

                for (final UnpaidBasketDto food : foodID) {

                    Query query = FirebaseDatabase.getInstance().getReference("Foods/" + collegeName + "/" + food.getFoodKey());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            checkOutBasket bask = new checkOutBasket(snapshot.getValue(FoodDto.class), food.getBasketKey());
                            bask.setUnpaidBasketDto(food);
                            foodAtBasketInModel.add(bask);
                            call.onCallBack(foodAtBasketInModel);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private void lookingAtBasket(final FirebaseCallbackForBasket1 call) {

        Query query = FirebaseDatabase.getInstance().getReference("Student/"+uid+"/Basket/");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot snap : snapshot.getChildren()) {

                    GenericTypeIndicator<List<Boolean>> t = new GenericTypeIndicator<List<Boolean>>() {};

                    UnpaidBasketDto basek = new UnpaidBasketDto(
                            snap.child("comment").getValue(String.class),
                            snap.child("owner").getValue(String.class),
                            snap.child("foodKey").getValue(String.class),
                            snap.child("buyer").getValue(String.class),
                            snap.child("buyerToken").getValue(String.class),
                            snap.child("noorder").getValue(int.class),
                            snap.child("addition").getValue(t)  );
                    basek.setBasketKey(snap.getKey());

                    foodAtBasket.add(basek);


//                    System.out.println(snap.child("foodKey").getValue(String.class));
//
//                    po.put("foodKey", snap.child("foodKey").getValue(String.class));
//                    po.put("basketKey", snap.getKey());
//                    op.add(po);

                }
//                foodAtBasket = op;
                call.onCallBack(foodAtBasket);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private interface FirebaseCallbackForBasket1 {
        void onCallBack(List<UnpaidBasketDto> st);
    }


    //callback for populate student home menu

    private interface FirebaseCallback1 {
        void onCallBack(List<FoodDto> st);
    }


    //------------------------------------------------------


    private interface foodFromUniversalBasket {
        void onCallBack(List<Map<String, Object>> foodID);
    }

    private interface RetriveItToRecycle {
        void onCallBack(List<checkOutBasket> foods);
    }

    //------------------------------------------------------

    private interface NotifyTheSeller {
        void onCallBack(List<BasketDto> basketId);
    }

    private interface messageSeller {
        void onCallBack(List<BasketDto> basketWithToken);

    }


}
