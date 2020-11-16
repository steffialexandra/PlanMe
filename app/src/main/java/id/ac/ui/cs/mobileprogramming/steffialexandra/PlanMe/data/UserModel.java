package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "user_model")
public class UserModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int userid;

    @ColumnInfo(name = "username")
    public String username;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ColumnInfo(name = "password")
    public String password;

}
