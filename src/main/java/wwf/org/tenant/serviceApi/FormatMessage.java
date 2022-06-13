package wwf.org.tenant.serviceApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormatMessage {

    public String format(BindingResult result){

        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    //error.put("err.getField()", err.getDefaultMessage());
                    error.put("message", err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errors);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

}
