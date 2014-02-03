package net.mytest;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

public class MyTest {

	public static final String ODB_NAME = "mytest.neodatis";
	
	public static void main(String[] args) {

		long lngStart = System.currentTimeMillis();
		
		ODB odb = null; 

		try {

			odb = ODBFactory.open( ODB_NAME );
			
			for ( int intRecordIndex=0; intRecordIndex<10000; intRecordIndex++ ) {

				CStoreData SD = new CStoreData();

				SD.strCommand = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();

				SD.strRecordID = UUID.randomUUID().toString();

				SD.Params = new LinkedHashMap<String,String>();

				int intMaxParams = 1 + (int)(Math.random()*5);	

				for ( int intParams = 0; intParams < intMaxParams; intParams++ ) {

					SD.Params.put( UUID.randomUUID().toString(), UUID.randomUUID().toString() + UUID.randomUUID().toString() );

				} 

				try {

					// Store the object
					odb.store( SD );

				} 
				finally {


				}


			}

		}
		finally {

			if (odb != null) {
				// Close the database
				odb.close();
			}

		}

		long lngEnd = System.currentTimeMillis();
		
		System.out.println( "Write elapsed time: " + Long.toString( lngEnd - lngStart ) + " ms" );
	
		Objects<CStoreData> ListObject; 
		
		lngStart = System.currentTimeMillis();
		
		try {

			odb = ODBFactory.open( ODB_NAME );

			ListObject = odb.getObjects( CStoreData.class );

		}
		finally {

			if (odb != null) {
				// Close the database
				odb.close();
			}

		}

		for ( CStoreData SD : ListObject ) {
			
			System.out.println( SD.strRecordID );
			
		}
		
		lngEnd = System.currentTimeMillis();
		
		System.out.println( "Read elapsed time: " + Long.toString( lngEnd - lngStart ) + " ms" );

		lngStart = System.currentTimeMillis();
		
		Objects<?> objects = null;
		
		try {

			odb = ODBFactory.open( ODB_NAME );

			objects = odb.getObjects( CStoreData.class );

			while ( objects.hasNext() ) {

				odb.delete( objects.next() );
			
			}
			
		}
		finally {

			if (odb != null) {
				// Close the database
				odb.close();
			}

		}

		for ( CStoreData SD : ListObject ) {
			
			System.out.println( SD.strRecordID );
			
		}
		
		lngEnd = System.currentTimeMillis();
		
		System.out.println( "Delete elapsed time: " + Long.toString( lngEnd - lngStart ) + " ms" );
		
	}

}
