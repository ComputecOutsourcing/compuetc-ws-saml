package co.experian.computec.sts.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class SAMLApplication extends Application {
 


        public Set<Class<?>> getClasses() {
            Set<Class<?>> classes = new HashSet<Class<?>>();
            classes.add(SAMLToken.class);
            classes.add(SAMLExceptionMapper.class);
            return classes;
        }
    }