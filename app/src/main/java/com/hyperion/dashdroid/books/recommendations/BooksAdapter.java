package com.hyperion.dashdroid.books.recommendations;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hyperion.dashdroid.R;
import com.hyperion.dashdroid.news.image.ImageLoader;

/**
 * Created by Valdrin on 6/2/2016.
 */
public class BooksAdapter extends BaseAdapter {

    private Bookshelf bookshelf;
    private LayoutInflater layoutInflater;
    public ImageLoader imageLoader;

    public BooksAdapter(BooksFragment fragment, Bookshelf bookshelf) {
        layoutInflater = (LayoutInflater) fragment.getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(fragment.getActivity().getApplicationContext());
        this.bookshelf = bookshelf;
    }

    @Override
    public int getCount() {
        return bookshelf.getItemCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        ImageView bookThumbnail;
        TextView bookTitle;
        TextView bookAuthor;
        RatingBar bookRating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();

        // Inflate the item layout and set the views
        View listItem = convertView;
        int pos = position;
        if (listItem == null){
            listItem = layoutInflater.inflate(R.layout.books_fragment_list_item, null);
        }

        // Initialize the views in the layout
        holder.bookThumbnail = (ImageView) listItem.findViewById(R.id.book_thumb);
        holder.bookTitle = (TextView) listItem.findViewById(R.id.book_title);
        holder.bookAuthor = (TextView) listItem.findViewById(R.id.book_author);
        holder.bookRating = (RatingBar) listItem.findViewById(R.id.book_ratingBar);
        holder.bookRating.getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#ffffff00"),
                        PorterDuff.Mode.MULTIPLY);

        // Set the views in the layout
        imageLoader.DisplayImage(bookshelf.getBookItem(pos).getBookThumbnail(), holder.bookThumbnail);
        holder.bookTitle.setText(bookshelf.getBookItem(pos).getTitle());
        holder.bookAuthor.setText(bookshelf.getBookItem(pos).getAuthor());
        holder.bookRating.setClickable(false);
        holder.bookRating.setNumStars((int)bookshelf.getBookItem(pos).getRating());
        listItem.setTag(holder);
        return listItem;
    }
}
