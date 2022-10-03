package mo.ed.amit.dayten.network.room.helper.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class InsertAsyncTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;
    private Context mContext;

    public InsertAsyncTask(Context mContext) {
        this.mContext = mContext;
        dialog=new ProgressDialog(mContext);
    }

    @Override
    protected String doInBackground(Void... voids) {
        // call api
        // call database
        // Background/Worker thread

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading ...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String st) {
        super.onPostExecute(st);

        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
