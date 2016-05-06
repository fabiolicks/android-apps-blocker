package br.com.getmo.appsblocker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by fabio.licks on 04/05/16.
 */

public class AppsListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.app_list, container, false );
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize( true );

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        new ListAppsTask().execute();
    }

    public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {
        private AppInfo[] mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            public ImageView mIcon;
            public TextView  mName;
            public CheckBox  mAllowed;

            public ViewHolder( View v ) {
                super( v );

                mIcon = ( ImageView )v.findViewById( R.id.app_icon );
                mName = ( TextView )v.findViewById( R.id.app_name );
                mAllowed = ( CheckBox )v.findViewById( R.id.app_enable );

                v.setOnClickListener( this );
            }

            @Override
            public void onClick( View v ) {
                int pos = mRecyclerView.getChildAdapterPosition( v );
                if ( pos >= 0 && pos < getItemCount() ) {
                    getActivity()
                            .startActivity(
                                    getActivity()
                                            .getPackageManager()
                                            .getLaunchIntentForPackage( mDataset[pos].appId ) );
                }
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public AppsAdapter( AppInfo[] dataset ) {
            mDataset = dataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public AppsAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater
                            .from( parent.getContext() )
                            .inflate( R.layout.app_list_item, parent, false );

            // set the view's size, margins, paddings and layout parameters
            return new ViewHolder( v );
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder( ViewHolder holder, int position ) {
            AppInfo item = mDataset[ position ];
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mIcon.setImageDrawable( item.appIcon );
            holder.mName.setText( item.appName );
            holder.mAllowed.setChecked( item.isAllowed );
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return ( mDataset != null ) ? mDataset.length : 0;
        }
    }

    private class ListAppsTask extends AsyncTask<Void, Void, AppInfo[]> {
        @Override
        protected AppInfo[] doInBackground(Void... params) {
//            ArrayList<AppInfo> result = new ArrayList<>();
            ArrayList<AppInfo> apps = AppsUtils.getApps( getActivity().getPackageManager() );

            AppInfo[] objArray = apps.toArray( new AppInfo[ 0 ] );
            Arrays.sort( objArray, new AppNameComparator() );

//            if ( apps != null ) {
//                for ( AppInfo app : apps ) {
//                    List<AppInfo> list = AppInfo.find( AppInfo.class, "APP_ID = ?", app.appId );
//                    if ( list != null && list.size() > 0 ) {
//                        result.add( list.get( 0 ) );
//                    } else {
//                        result.add( app );
//                    }
//                }
//            }

//            return result;
            return objArray;
        }

        @Override
        protected void onPostExecute( AppInfo[] dataset ) {
            if ( dataset != null ) {
                // specify an adapter (see also next example)
                mAdapter = new AppsAdapter( dataset );
                mRecyclerView.setAdapter( mAdapter );
            }
        }
    }
}
