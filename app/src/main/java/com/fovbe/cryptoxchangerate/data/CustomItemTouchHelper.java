package com.fovbe.cryptoxchangerate.data;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by OWNER1 on 10/18/2017.
 */

public class CustomItemTouchHelper extends ItemTouchHelper.Callback {
    private CustomItemTouchHelperListener listener;

    private Resources res;

    public CustomItemTouchHelper(CustomItemTouchHelperListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundv = ((CurrencyAdapter.ViewHolder) viewHolder).cardBinding.viewForeground;
            getDefaultUIUtil().onSelected(foregroundv);
            //super.onSelectedChanged(viewHolder, actionState);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //listener.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (dX < 0 ) {
                final View foregroundv = ((CurrencyAdapter.ViewHolder) viewHolder).cardBinding.viewForeground;
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundv, dX, dY, actionState, isCurrentlyActive);
            }
            else super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        listener.onMove(viewHolder, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundv = ((CurrencyAdapter.ViewHolder) viewHolder).cardBinding.viewForeground;
        getDefaultUIUtil().clearView(foregroundv);
        //super.clearView(recyclerView, viewHolder);
    }

    public interface CustomItemTouchHelperListener {

        /**
         * Called when an item has been dismissed by a swipe.<br/>
         * <br/>
         * Implementations should call {@link RecyclerView.Adapter#notifyItemRemoved(int)} after
         * adjusting the underlying data to reflect this removal.
         *
         * @param position The position of the item dismissed.
         *
         * @see RecyclerView#getAdapterPositionFor(RecyclerView.ViewHolder)
         * @see RecyclerView.ViewHolder#getAdapterPosition()
         */

        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);

        /* Called when an item has been dragged far enough to trigger a move. This is called every time
         * an item is shifted, and <strong>not</strong> at the end of a "drop" event.<br/>
         * @param fromPosition The start position of the moved item.
         * @param toPosition   Then resolved position of the moved item.
         * @return True if the item was moved to the new adapter position.
         */
        void onMove(RecyclerView.ViewHolder viewHolder, int fromPosition, int toPosition);
        }
}
