import android.app.AlertDialog;


/**
 * Created by Administrator on 2017/5/22 0022.
 */
//Builder 设计模式
public class Person {
    private String name;
    private int age;
    private String fire;

    
    private Person(Builder builder){
        this.name = builder.name;
        this.age = builder.age;
        this.fire = builder.fire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private class Builder {
        private String name;
        private int age;
        private String fire;
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setAge(int age){
            this.age = age;
            return this;
        }
        public Builder setFire(String fire){
            this.fire = fire;
            return this;
        }

    }
}

