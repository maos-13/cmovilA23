package com.example.parcial02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.parcial02.model.Empleado;
import com.example.parcial02.sinterface.CrudEmpleadoInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Empleado> listEmpleado;
    private final String BASE_URL = "http://192.168.101.14:8081/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Empleado newEmpleado = new Empleado();
        newEmpleado.setUser("Juan");
        newEmpleado.setPassword("123456");
        newEmpleado.setEmail("juan@gmail.com");

        createEmpleado(newEmpleado);
    }

    private void createEmpleado(Empleado empleado) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CrudEmpleadoInterface crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
        Call<Empleado> call = crudEmpleado.create(empleado);

        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(@NonNull Call<Empleado> call, @NonNull Response<Empleado> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err:", response.message());
                    showToast("Error al crear empleado.");
                    return;
                }
                Empleado empleadoResponse = response.body();
                assert empleadoResponse != null;
                Log.i("Empleado creado: ", empleadoResponse.toString());
                showToast("Empleado creado correctamente.");
            }

            @Override
            public void onFailure(@NonNull Call<Empleado> call, @NonNull Throwable t) {
                Log.e("Throw error:", t.getMessage());
                showToast("Error al crear empleado.");
            }
        });
    }

    private void getAll() {
        new GetAllTask().execute();
    }

    private class GetAllTask extends AsyncTask<Void, Void, List<Empleado>> {

        @Override
        protected List<Empleado> doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CrudEmpleadoInterface crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
            Call<List<Empleado>> call = crudEmpleado.getAll();

            try {
                Response<List<Empleado>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    Log.e("Response err:", response.message());
                }
            } catch (IOException e) {
                Log.e("Throw error:", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Empleado> empleados) {
            if (empleados != null) {
                listEmpleado = empleados;
                listEmpleado.forEach(p -> Log.i("Empleados: ", p.toString()));
            } else {
                showToast("Error al obtener lista de empleados.");
            }
        }
    }

    private void showToast(String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }
    private void updateEmpleado(long id, String nuevoNombre, String nuevoEmail) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CrudEmpleadoInterface crudEmpleado = retrofit.create(CrudEmpleadoInterface.class);
        Call<Empleado> call = crudEmpleado.updateNameAndEmail(id, nuevoNombre, nuevoEmail);

        updateEmpleado(5, "Nuevo nombre", "nuevo.correo@ejemplo.com");

        call.enqueue(new Callback<Empleado>() {
            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err:", response.message());
                    showToast("Error al actualizar empleado.");
                    return;
                }
                Empleado empleadoResponse = response.body();
                assert empleadoResponse != null;
                Log.i("Empleado actualizado: ", empleadoResponse.toString());
                showToast("Empleado actualizado correctamente.");
            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                Log.e("Throw error:", t.getMessage());
                showToast("Error al actualizar empleado.");
            }
        });
    }

}
