package com.assem.tests.iotblue.Utils;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class RecyclerViewEmptySupport extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && adapter.getItemCount() > 0) {
                if (emptyView != null) emptyView.setVisibility(View.GONE);
                RecyclerViewEmptySupport.this.setVisibility(View.VISIBLE);
            } else {
                if (emptyView != null) {
                    emptyView.setVisibility(View.VISIBLE);
                    RecyclerViewEmptySupport.this.setVisibility(View.GONE);
                }
            }

        }
    };

    public RecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null)
            adapter.registerAdapterDataObserver(emptyObserver);
        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        if (emptyView != null) {
            this.emptyView = emptyView;
            ((RelativeLayout) RecyclerViewEmptySupport.this.getParent()).addView(this.emptyView);
        }
    }

    public void setBlankSlateView(View emptyView) {
        if (emptyView != null) {
            this.emptyView = emptyView;
        }
    }


}
