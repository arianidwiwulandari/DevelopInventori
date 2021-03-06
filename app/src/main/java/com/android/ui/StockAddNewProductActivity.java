package com.android.ui;

import com.controller.InventoryController;
import com.core.ItemDescription;
import com.android.softspectproject.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * StockAddNewProductActivity is the Activity to add the new ItemDescription.
 * @author Sikarin	Larnamwong	5510546174
 *
 */
public class StockAddNewProductActivity extends Activity {
	/**
	 * txtName is the name of the Item EditText.
	 */
	private EditText txtName;
	/**
	 * txtPrice is the name of the Item EditPrice.
	 */
	private EditText txtPrice;
	/**
	 * txtBarcode is the barcode of the Item EditText.
	 */
	private EditText txtBarcode;
	/**
	 * txtProductDescription is the product description of the Item EditText
	 */
	private EditText txtProductDescription;
	/**
	 * txtCost is the cost of the Item EditText
	 */
	private EditText txtCost;
	/**
	 * btScanBarcode is the scan barcode Button.
	 */
	private Button btScanBarcode;
	/**
	 * btOK is the Button to Confirm barcode input.
	 */
	private Button btOK;
	/**
	 * inventoryController is the instance of the InventoryController.
	 */
	private InventoryController inventoryController;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_add_new_product);
        
        inventoryController = InventoryController.getInstance();
        
        txtCost = (EditText)findViewById(R.id.txtRegisterCost);
        txtName = (EditText)findViewById(R.id.txtSearchProduct);
        txtPrice = (EditText)findViewById(R.id.txtAddNewProducrDescriptionPrice);
        txtBarcode = (EditText)findViewById(R.id.txtRegisterBarcode);
        txtProductDescription = (EditText)findViewById(R.id.txtRegisterProductionDescription);
        btOK = (Button)findViewById(R.id.btRegisterOK);
        btScanBarcode = (Button)findViewById(R.id.btAddNewProductDescriptionScanBarcode);
        btScanBarcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
					startActivityForResult(intent, 0);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),"Please Install Barcode Scanner",Toast.LENGTH_SHORT).show();
				}

			}
		});
        btOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String name = txtName.getText().toString();
					int cost = Integer.parseInt(txtCost.getText().toString());
					float price = Float.parseFloat(txtPrice.getText().toString());
					String barcode =  txtBarcode.getText().toString();
					String productDescription = txtProductDescription.getText().toString();
					
					ItemDescription itemDescription = inventoryController.createNewItemDescription(getApplicationContext(), name, productDescription, price, barcode,cost);
					ItemDescription itemGet = inventoryController.getItemDescriptionByBarcode(getApplicationContext(), barcode);
					
					final AlertDialog alertDialog1 = new AlertDialog.Builder(StockAddNewProductActivity.this).create();
					 
		            alertDialog1.setTitle("Product Details");
		 
		            alertDialog1.setMessage("Product Description Added!");
		
		            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {
		            	
		                public void onClick(DialogInterface dialog, int which) {
		                	//finish();
		                	txtName.setText("");
		                	txtPrice.setText("");
		                	txtBarcode.setText("");
		                	txtProductDescription.setText("");
		                	txtCost.setText("");
		                }
		            });
		            alertDialog1.show();	
					
				} catch (Exception e) {
					final AlertDialog alertDialog1 = new AlertDialog.Builder(StockAddNewProductActivity.this).create();
		 
		            alertDialog1.setTitle("Product Details");
		 
		            alertDialog1.setMessage("Plaese fill the correctly data!\n" + e.toString());
		
		            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {
		            	
		                public void onClick(DialogInterface dialog, int which) {
		                	
		                }
		            });
		            alertDialog1.show();	
				}
				
				
			}
		});
        
    }
    /**
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {

				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

				txtBarcode.setText(contents);
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stock_add_new_product, menu);
        return true;
    }
    
}
