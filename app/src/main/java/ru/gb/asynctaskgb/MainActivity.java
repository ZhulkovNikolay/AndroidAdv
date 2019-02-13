package ru.gb.asynctaskgb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

  MyTask myTask;
  TextView textViewInfo;
  ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textViewInfo = findViewById(R.id.tvInfo);
  }

  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.btnStart:
        myTask = new MyTask();
        myTask.execute();
        break;
      case R.id.btnGet:
        showResult();
        break;
      default:
        break;
    }
  }

  private void showResult() {
    if (myTask == null) return;
    int result;

    try {
      result = myTask.get(1, TimeUnit.SECONDS);
      Toast.makeText(this, "get returns " + result, Toast.LENGTH_LONG).show();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }

  class MyTask extends AsyncTask<Void, Void, Integer> {
    @Override
    protected Integer doInBackground(Void... urls) {

      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 2222;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      textViewInfo.setText("Запуск AsyncTask");
      progressBar = findViewById(R.id.progressBar);
      progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(Integer result) {
      super.onPostExecute(result);
      textViewInfo.setText("Завершен AsyncTask, результат = " + result);
      progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
  }
}
