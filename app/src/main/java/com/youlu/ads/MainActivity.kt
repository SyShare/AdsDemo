package com.youlu.ads

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.youlu.ads.lockscreenad.LockScreenService
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType


class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        app_title.setOnClickListener {
//            val methods = Test::class.java.getMethods()
//
//            for (i in methods.indices) {
//                val oneMethod = methods[i]
//
//                if (oneMethod.getName().equals("test")) {
//                    val types = oneMethod.getGenericParameterTypes()
//
//                    //第一个参数，TestReflect p0
//                    val type0 = types[0] as Class<*>
//                    println("type0:" + type0.name)
//
//                    //第二个参数，List<TestReflect> p1
//                    val type1 = types[1]
//                    val parameterizedType1 = (type1 as ParameterizedType).actualTypeArguments
//                    val parameterizedType1_0 = parameterizedType1[0] as Class<*>
//                    println("parameterizedType1_0:" + parameterizedType1_0.name)
//
//                    //第三个参数，Map<String,TestReflect> p2
//                    val type2 = types[2]
//                    val parameterizedType2 = (type2 as ParameterizedType).actualTypeArguments
//                    val parameterizedType2_0 = parameterizedType2[0] as Class<*>
//                    println("parameterizedType2_0:" + parameterizedType2_0.name)
//                    val parameterizedType2_1 = parameterizedType2[1] as Class<*>
//                    println("parameterizedType2_1:" + parameterizedType2_1.name)
//
//
//                    //第四个参数，List<String>[] p3
//                    val type3 = types[3]
//                    val genericArrayType3 = (type3 as GenericArrayType).getGenericComponentType()
//                    val parameterizedType3 = genericArrayType3 as ParameterizedType
//                    val parameterizedType3Arr = parameterizedType3.actualTypeArguments
//                    val class3 = parameterizedType3Arr[0] as Class<*>
//                    println("class3:" + class3.name)
//
//                    //第五个参数，Map<String,TestReflect>[] p4
//                    val type4 = types[4]
//                    val genericArrayType4 = (type4 as GenericArrayType).getGenericComponentType()
//                    val parameterizedType4 = genericArrayType4 as ParameterizedType
//                    val parameterizedType4Arr = parameterizedType4.actualTypeArguments
//                    val class4_0 = parameterizedType4Arr[0] as Class<*>
//                    println("class4_0:" + class4_0.name)
//                    val class4_1 = parameterizedType4Arr[1] as Class<*>
//                    println("class4_1:" + class4_1.name)
//
//
//                    //第六个参数，List<? extends TestReflect> p5
//                    val type5 = types[5]
//                    val parameterizedType5 = (type5 as ParameterizedType).actualTypeArguments
//                    val parameterizedType5_0_upper = (parameterizedType5[0] as WildcardType).getUpperBounds()
//                    val parameterizedType5_0_lower = (parameterizedType5[0] as WildcardType).getLowerBounds()
//
//                    //第七个参数，Map<? extends TestReflect,? super TestReflect> p6
//                    val type6 = types[6]
//                    val parameterizedType6 = (type6 as ParameterizedType).actualTypeArguments
//                    val parameterizedType6_0_upper = (parameterizedType6[0] as WildcardType).getUpperBounds()
//                    val parameterizedType6_0_lower = (parameterizedType6[0] as WildcardType).getLowerBounds()
//                    val parameterizedType6_1_upper = (parameterizedType6[1] as WildcardType).getUpperBounds()
//                    val parameterizedType6_1_lower = (parameterizedType6[1] as WildcardType).getLowerBounds()
//
//                }
//
//            }
//        }


        registerBroasdCast();
    }

    private fun registerBroasdCast() {
        val intent = Intent()
        intent.action = LockScreenService.LOCK_SCREEN_ACTION
        sendBroadcast(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
}
