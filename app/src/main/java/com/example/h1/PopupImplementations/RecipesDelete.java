package com.example.h1.PopupImplementations;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.h1.AssigmentActivity.TaskList;
import com.example.h1.R;
import com.example.h1.RecipeActivity.RecipeDB.Recipe;
import com.example.h1.RecipeActivity.RecipeList;

public class RecipesDelete implements PopupInterface {
    @Override
    public void create(final View view, final int id){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.recipes_popup_delete, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow -> focusable: true
        //Create a window with our parameters, deleted final
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);


        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler
        //final EditText editText = popupView.findViewById(R.id.titleTask);
        //editText.setText(R.string.DefName);

        // editText for assigning name to a task
        Button verifyDelete = popupView.findViewById(R.id.recipe_remove);

        //deleting recipe from database
        verifyDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeList.database.recipeDAO().delete(id);
                RecipeList.adapter.reload(RecipeList.Taste, RecipeList.Type);
                popupWindow.dismiss();
            }
        });

        Button cancelDelete = popupView.findViewById(R.id.recipe_cancel);

        //cancel deleting recipe
        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}