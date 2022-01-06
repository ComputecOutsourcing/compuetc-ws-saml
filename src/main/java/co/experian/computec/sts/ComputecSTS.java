package co.experian.computec.sts;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import org.apache.axiom.om.util.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import co.experian.computec.sts.model.ComputecSTSDTO;

/**
 * Calse de Service Token Secure
 * @author jvelandia
 * @FechaCreacion 15/05/2012
 * @FechaModificacion 15/05/2012
 * @version 1.2  //private static final String RECURSOS_PROPERTIES="recursos.properties";
 */
public class ComputecSTS{

    private static final String USUARIO_NO_VALIDO="Usuario no válido";

    private static final String USUARIOS_AUTORIZADOS="users.properties";

    private final static Logger LOGGER=Logger.getLogger(ComputecSTS.class.getName());

    /**
     * Obtiene Token de SAML
     * @param valores
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked","deprecation"})
    public String obtenerToken(ComputecSTSDTO valores) throws Exception{

        InputStream stream=null;
        InitialContext cx=null;
        try{

            Properties props=new Properties();
            String os=System.getProperty("os.name").toLowerCase();

            if(isWindows(os)){
                stream=new FileInputStream("C:/Datos/RecursosSAML/"+USUARIOS_AUTORIZADOS);
            }
            else{
                stream=new FileInputStream("/Datos/RecursosSAML/"+USUARIOS_AUTORIZADOS);
            }

            props.load(stream);

            if(!(props.containsValue(valores.getUser())&&props.containsValue(valores.getPassword()))){
                throw new Exception(USUARIO_NO_VALIDO);
            }

            long timeIni=System.currentTimeMillis();

            long timeFin=System.currentTimeMillis();

            LOGGER.info("tiempo ctx: "+(timeFin-timeIni));

            timeIni=System.currentTimeMillis();

            timeFin=System.currentTimeMillis();

            LOGGER.info("tiempo conf: "+(timeFin-timeIni));

            timeIni=System.currentTimeMillis();

            SecureRandom sr=SecureRandom.getInstance("SHA1PRNG");

            String responseToken=new BigInteger(130,sr).toString(32);
            StringBuilder sb=new StringBuilder(responseToken);
            sb.append("+");
            DateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sb.append(format.format(GregorianCalendar.getInstance().getTime()));

            timeFin=System.currentTimeMillis();

            LOGGER.info("tiempo inv: "+(timeFin-timeIni));

            cx=new InitialContext();
            Map<String,Object> map=null;
            try{
                LOGGER.debug("Colocando el token en el contexto TOKEN: "+responseToken);
                map=(Map<String,Object>)cx.lookup(responseToken);
                map.put(responseToken,sb.toString());
                cx.rebind(responseToken,map);
                LOGGER.debug("fin por primera ves Colocando el token en el contexto TOKEN: "+responseToken);
            }
            catch(NameNotFoundException e){
                LOGGER.debug("No se encuentra lo coloco Colocando el token en el contexto TOKEN: "+responseToken);
                map=new HashMap<String,Object>();
                map.put(responseToken,sb.toString());
                cx.rebind(responseToken,map);
                LOGGER.debug("fin Colocando el token en el contexto TOKEN: "+responseToken);
            }

            String objSerializado=serializarBase64(responseToken);
            LOGGER.debug("TOKEN Final Serializado: "+objSerializado);
            return objSerializado;

        }
        catch(Exception e){
            LOGGER.log(Priority.FATAL,"exception in ComputecSTS - validate token "+e.getLocalizedMessage(),e.getCause());
            throw new Exception(e.getMessage());
        }
        finally{
            try{
                if(stream!=null){
                    stream.close();
                }
                cx.close();
            }
            catch(Exception e){
                LOGGER.log(Priority.FATAL,"exception in ComputecSTS "+e.getLocalizedMessage(),e);
            }
        }

    }

    /**
     * Serializa un objeto devuelve el base 64
     * @param objeto
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private static String serializarBase64(Object objeto) throws Exception{

        ByteArrayOutputStream baos=null;
        ObjectOutputStream oout=null;
        try{
            String objetoSerializado=null;

            baos=new ByteArrayOutputStream();
            oout=new ObjectOutputStream(baos);
            oout.writeObject(objeto);

            byte[] buf=baos.toByteArray();
            objetoSerializado=Base64.encode(buf);

            return objetoSerializado;
        }
        catch(Exception e){
            LOGGER.log(Priority.FATAL,"exception in ComputecSTS - validate token "+e.getLocalizedMessage(),e.getCause());
            throw new Exception(e.getMessage());
        }
        finally{
            try{
                if(baos!=null&&oout!=null){
                    baos.close();
                    oout.close();    
                }
                
            }
            catch(IOException e){
                LOGGER.log(Priority.FATAL,"exception in ComputecSTS "+e.getLocalizedMessage(),e);
            }
        }
    }

    /**
     * 
     * @param os
     * @return
     */
    public boolean isWindows(String os){
        return(os.indexOf("win")>=0);
    }

    /**
     * 
     * @param os
     * @return
     */
    public boolean isMac(String os){
        return(os.indexOf("mac")>=0);
    }

    /**
     * 
     * @param os
     * @return
     */
    public boolean isUnix(String os){
        return(os.indexOf("nix")>=0||os.indexOf("nux")>=0||os.indexOf("aix")>0);
    }

    /**
     * 
     * @param os
     * @return
     */
    public boolean isSolaris(String os){
        return(os.indexOf("sunos")>=0);
    }

}
