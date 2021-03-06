package com.cmweb.cognos8;

/**
 * BaseClassWrapper.java
 * 
 * Copyright (C) 2008 Cognos ULC, an IBM Company. All rights reserved.
 * Cognos (R) is a trademark of Cognos ULC, (formerly Cognos Incorporated).
 *
 * Thin wrapper class for keeping BaseClass objects in GUI containers
 * 
 */

import com.cognos.developer.schemas.bibus._3.BaseClass;

public class BaseClassWrapper {

	private BaseClass myBaseClass = null;

	// constructor
	public BaseClassWrapper(BaseClass newBaseClass) {
		myBaseClass = newBaseClass;
	}

	public BaseClass getBaseClassObject() {
		return myBaseClass;
	}

	public void setBaseClassObject(BaseClass newBaseClassObject) {
		myBaseClass = newBaseClassObject;
	}

	// Override toString()
	public String toString() {
		if (myBaseClass != null) {
			return myBaseClass.getDefaultName().getValue();
		}

		return null;
	}

	public String getSearchPath() {
		if (myBaseClass != null) {
			return myBaseClass.getSearchPath().getValue();
		}

		return null;
	}

}
