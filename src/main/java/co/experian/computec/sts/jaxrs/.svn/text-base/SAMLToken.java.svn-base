package co.experian.computec.sts.jaxrs;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.wink.json4j.JSONObject;
import co.experian.computec.sts.ComputecSTS;
import co.experian.computec.sts.model.ComputecSTSDTO;

@javax.ws.rs.Path("/saml/gettoken")
public class SAMLToken{

    private final static Logger LOGGER=Logger.getLogger(SAMLToken.class.getName());

    public SAMLToken(){
        /* no argument constructor */
    }

    @javax.ws.rs.GET
    @Produces("application/json") 
    public Response getToken(@javax.ws.rs.QueryParam("user") String user,@javax.ws.rs.QueryParam("password") String password) throws Exception{

        String str=null;
        ResponseBuilder builder=null;
        try{
//            JSONObject jason=new JSONObject();
//            response.setContentType("text/javascript");
//            String callback = request.getParameter("jsoncallback"); 
            
            
            ComputecSTS sts=new ComputecSTS();

            ComputecSTSDTO dto=new ComputecSTSDTO();
            dto.setUser(user);
            dto.setPassword(password);
            String tmp="someCallback(";
            str=sts.obtenerToken(dto);
            str = new JSONObject().put("token",str).toString();
            String retorno=tmp.concat(str);
            retorno=retorno.concat(")");
            builder=Response.ok(retorno, MediaType.APPLICATION_JSON_TYPE);
            builder.header("Access-Control-Allow-Origin","*");
            builder.header("Access-Control-Max-Age","3600");
            builder.header("Access-Control-Allow-Methods","*");
            builder.header("Access-Control-Allow-Headers","X-Requested-With,Host,User-Agent,Accept,Accept-Language,Accept-Encoding,Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin");
            //someCallback()
            
         //   Response.ok(str, MediaType.APPLICATION_JSON).build();
//            jason.put("token",str); 
            
//            response.getOutputStream().print(jason.toString()); 
//            response.flushBuffer(); 

            
        }
        catch(Exception e){
            LOGGER.log(Level.SEVERE,"exception in SAMLToken - validate token "+e.getLocalizedMessage(),e.getCause());
            throw e;
        }

        return builder.build();
     //  return Response.ok(str, MediaType.APPLICATION_JSON).build();
    }
}
