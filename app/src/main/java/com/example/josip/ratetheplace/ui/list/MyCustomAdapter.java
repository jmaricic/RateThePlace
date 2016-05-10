package com.example.josip.ratetheplace.ui.list;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.josip.ratetheplace.R;
import com.example.josip.ratetheplace.model.Hotel;
import com.example.josip.ratetheplace.ui.map.myHotels.editMyHotel.MyHotelDetailsMapActivity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josip on 6.4.2016..
 */
public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyViewHolder> implements ChildEventListener {

    private Firebase hotelRef;
    private LayoutInflater inflater;
    private ArrayList<Hotel> hotels = new ArrayList<>();
    private Context context;

    public MyCustomAdapter(Context context, Firebase hotelRef) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.hotelRef = hotelRef;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_list_item, parent, false);
        return new MyViewHolder(view, context, hotels);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Hotel currentHotel = hotels.get(position);
        holder.mHotelName.setText(currentHotel.getName());
        holder.mHotelRating.setRating(currentHotel.getRating());
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public void clear() {
        hotels.clear();
    }

    public Hotel get(int position) {
        return hotels.get(position);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        DataSnapshot hotelsSnapshot = dataSnapshot.child("hotel");
        for (DataSnapshot hotelSnapshot : hotelsSnapshot.getChildren()) {
            Hotel hotel = hotelSnapshot.getValue(Hotel.class);
            if(!dataSnapshot.getKey().equals(hotelRef.getAuth().getUid())) {
                notifyDataSetChanged();
                break;
            } else {
                hotels.add(0, hotel);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //// TODO: 18.4.2016.
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        //// TODO: 18.4.2016.
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mHotelName;
        private RatingBar mHotelRating;
        private List<Hotel> hotels = new ArrayList<>();
        private Context ctx;

        public MyViewHolder(View itemView, Context ctx, ArrayList<Hotel> hotels) {
            super(itemView);
            this.hotels = hotels;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            mHotelName = (TextView) itemView.findViewById(R.id.hotel_name_row);
            mHotelRating = (RatingBar) itemView.findViewById(R.id.rating_bar_row);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Hotel hotel = this.hotels.get(position);
            Intent intent = new Intent(this.ctx, MyHotelDetailsMapActivity.class);
            intent.putExtra("author", hotel.getAuthor());
            intent.putExtra("food", String.valueOf(hotel.getFood()));
            intent.putExtra("service", String.valueOf(hotel.getService()));
            intent.putExtra("comfort", String.valueOf(hotel.getComfort()));
            intent.putExtra("name", hotel.getName());
            intent.putExtra("review", hotel.getReview());
            intent.putExtra("latit", String.valueOf(hotel.getLatit()));
            intent.putExtra("longit", String.valueOf(hotel.getLongit()));
            intent.putExtra("rating", String.valueOf(hotel.getRating()));
            this.ctx.startActivity(intent);
        }

    }
}
