package encryptionRSA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


// Class containing two methods that serialize and deserialize objects that are passed in
public class Serializer {

	// Method to serialize an object into an array of bytes
	public static byte[] serialize(Object object) throws IOException {
		try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
			try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)){
				objectOutputStream.writeObject(object);
			}
			return byteArrayOutputStream.toByteArray();
		}
	}

	// Method to deserialize an array of bytes back to an object
	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)){
			try(ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){
				return objectInputStream.readObject();
			}
		}
	}
}