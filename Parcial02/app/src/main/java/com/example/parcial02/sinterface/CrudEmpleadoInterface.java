package com.example.parcial02.sinterface;

import com.example.parcial02.model.Empleado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface CrudEmpleadoInterface {

    @GET("consultarAll")
    Call<List<Empleado>> getAll();

    @GET("consultar/{id}")
    Call<Empleado> getById(@Path("id") long id);

    @POST("guardar")
    Call<Empleado> create(@Body Empleado empleado);

    @PUT("actualizar/{id}")
    Call<Empleado> update(@Path("id") long id, @Body Empleado empleado);

    @PUT("empleados/{id}/nombre-y-email")
    Call<Empleado> updateNameAndEmail(@Path("id") long id, @Query("nombre") String nuevoNombre, @Query("email") String nuevoEmail);

    @DELETE("eliminar/{id}")
    Call<Void> delete(@Path("id") long id);

}
