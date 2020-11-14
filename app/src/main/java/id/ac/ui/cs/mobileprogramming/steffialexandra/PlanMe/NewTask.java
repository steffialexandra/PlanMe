package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public class NewTask extends AppCompatActivity {

        EditText newtitle, newdesc;
        CheckBox newpriority;
        DatePicker newdate;
        TextView tasktitle, desc, taskdate, priority;
        Button saveButton, cancelButton;

        Integer number = new Random().nextInt();
        String taskid = Integer.toString(number);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_task_layout);

            tasktitle = findViewById(R.id.tasktitle);
            desc = findViewById(R.id.desc);
            taskdate = findViewById(R.id.taskdate);
            priority = findViewById(R.id.priority)
            newtitle = findViewById(R.id.newtitle);
            newdesc = findViewById(R.id.newdesc);
            newdate = findViewById(R.id.newdate);
            newpriority = findViewById(R.id.newpriority);
            saveButton = findViewById(R.id.saveButton);
            cancelButton = findViewById(R.id.cancelButton);

            //parsing datepicker value to string
            String date = newdate.toString();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* // insert data to database
                    reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
                            child("Does" + doesNum);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                            dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                            dataSnapshot.getRef().child("datedoes").setValue(datedoes.getText().toString());
                            dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                            Intent a = new Intent(NewTaskAct.this,MainActivity.class);
                            startActivity(a);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                }
            });
    }
}
