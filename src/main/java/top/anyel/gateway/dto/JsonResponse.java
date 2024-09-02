package top.anyel.gateway.dto;


import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class JsonResponse {

    private Boolean respuesta;
    private Integer codeStatus = HttpStatus.OK.value();
    private String mensaje;
    private Object resultado;
    private String detalleError;

    public JsonResponse(Boolean respuesta, Object resultado) {
        this.respuesta = respuesta;
        this.resultado = resultado;
    }

    public JsonResponse(Boolean respuesta, String mensaje) {
        this.respuesta = respuesta;
        this.mensaje = mensaje;
    }

    public JsonResponse(Boolean respuesta, String mensaje, Object resultado) {
        this.respuesta = respuesta;
        this.mensaje = mensaje;
        this.resultado = resultado;
    }

    public JsonResponse(Boolean respuesta, Integer codeStatus, Object resultado) {
        this.respuesta = respuesta;
        this.codeStatus = codeStatus;
        this.resultado = resultado;
    }

    public JsonResponse(Boolean respuesta, Integer codeStatus, String mensaje) {
        this.respuesta = respuesta;
        this.codeStatus = codeStatus;
        this.mensaje = mensaje;
    }

    public JsonResponse(Boolean respuesta, Integer codeStatus, String mensaje, Object resultado) {
        this.respuesta = respuesta;
        this.codeStatus = codeStatus;
        this.mensaje = mensaje;
        this.resultado = resultado;
    }

    public JsonResponse(Boolean respuesta, Integer codeStatus, String mensaje, Object resultado,
                        String detalleError) {
        this.respuesta = respuesta;
        this.codeStatus = codeStatus;
        this.mensaje = mensaje;
        this.resultado = resultado;
        this.detalleError = detalleError;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}