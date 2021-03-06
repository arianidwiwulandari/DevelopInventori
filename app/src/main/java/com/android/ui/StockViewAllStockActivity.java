package com.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.android.softspectproject.R;
import com.android.softspectproject.R.layout;
import com.android.softspectproject.R.menu;
import com.controller.InventoryController;
import com.core.Cashier;
import com.core.InventoryLineItem;
import com.core.Item;
import com.utils.DateManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * StockViewAllStockActivity is the view all InventoryLineItem Activity.
 * @author Sikarin	Larnamwong	5510546174
 *
 */
public class StockViewAllStockActivity extends Activity {
	/**
	 * listViewStock is the InventoryLineItem ListView.
	 */
	private ListView listViewStock;
	/**
	 * adapter is StockViewAllStockCustomArrayAdapter.
	 */
	private StockViewAllStockCustomArrayAdapter adapter;
	/**
	 * inventoryLineItems is the ArrayList of the InventoryLineItem.
	 */
	private List<InventoryLineItem> inventoryLineItems= new ArrayList<InventoryLineItem>();
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
		inventoryController = InventoryController.getInstance();
		inventoryLineItems.clear();
		for(InventoryLineItem i : inventoryController.getAllInventoryLineItem(getApplicationContext())){
			inventoryLineItems.add(i);
			
			if(i.getCashier()==null) Toast.makeText(getApplicationContext(), "Cashier Null", Toast.LENGTH_LONG).show();
		}
		
		setContentView(R.layout.activity_stock_view_all_stock);
		
		adapter = new StockViewAllStockCustomArrayAdapter(this, inventoryLineItems);
		listViewStock = (ListView)findViewById(R.id.listViewViewAllStock);
		listViewStock.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		listViewStock.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				AlertDialog.Builder builder = new AlertDialog.Builder(StockViewAllStockActivity.this);
				builder.setTitle("Stock Details");
				final InventoryLineItem invenCurrent = inventoryLineItems.get(arg2);
				String s =	"ID: " + invenCurrent.getID()+
							"\nDate: " + DateManager.getDateString(invenCurrent.getDate());
						
				
				builder.setMessage(s);
				
				builder.setNegativeButton("Edit",new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0,int arg1) {
								Intent intent = new Intent(getApplicationContext(), StockDetailsActivity.class);
								intent.putExtra("inventoryLineItem", invenCurrent);
								startActivity(intent);
								
							}
						});
				builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});

				builder.show();

			}

		});
		
		
		
	}

	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		inventoryLineItems.clear();
		for(InventoryLineItem i : inventoryController.getAllInventoryLineItem(getApplicationContext())){
			inventoryLineItems.add(i);
		}
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stock_view_all_stock, menu);
		return true;
	}

}
