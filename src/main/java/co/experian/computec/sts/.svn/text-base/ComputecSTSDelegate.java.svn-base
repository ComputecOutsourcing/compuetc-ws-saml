package co.experian.computec.sts;

import co.experian.computec.sts.model.ComputecSTSDTO;

@javax.jws.WebService(targetNamespace = "http://sts.computec.experian.co/", serviceName = "ComputecSTSService", portName = "ComputecSTSPort")

public class ComputecSTSDelegate {

	co.experian.computec.sts.ComputecSTS computecSTS = new co.experian.computec.sts.ComputecSTS();

	public String obtenerToken(ComputecSTSDTO valores) throws Exception {
		return computecSTS.obtenerToken(valores);
	}

}