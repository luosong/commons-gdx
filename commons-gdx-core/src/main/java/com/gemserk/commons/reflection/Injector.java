package com.gemserk.commons.reflection;

/**
 * Provides a very basic dependency injection by calling setter methods over the fields of an object.
 * 
 * @author acoppes
 */
public interface Injector {

	/**
	 * For each field of the object, configures its value using the registered values with the add() method.
	 * 
	 * @param instance
	 *            The object to be injected.
	 */
	void injectMembers(Object instance);

	/**
	 * Instantiates a new instance of the specified class, configures it and internally caches it.
	 */
	<T> T getInstance(Class<? extends T> clazz);

	/**
	 * Registers a new object to be injected in the fields named with specified name (similar to bind method, name could be changed)
	 * 
	 * @param fieldName
	 *            The name of the field to inject the object to.
	 * @param instance
	 *            The instance to be injected.
	 */
	void bind(String fieldName, Object instance);

	// TODO: simple bind between an object instance and its class to be injected for every field of that class
	// void bind(Object instance)

}