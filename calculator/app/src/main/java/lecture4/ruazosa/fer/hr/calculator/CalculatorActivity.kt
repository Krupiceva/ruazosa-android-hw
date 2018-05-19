package lecture4.ruazosa.fer.hr.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class CalculatorActivity : AppCompatActivity() {

    private var resultView: TextView? = null
    private var buttonZero: Button? = null
    private var buttonOne: Button? = null
    private var buttonTwo: Button? = null
    private var buttonThree: Button? = null
    private var buttonFour: Button? = null
    private var buttonFive: Button? = null
    private var buttonSix: Button? = null
    private var buttonSeven: Button? = null
    private var buttonEight: Button? = null
    private var buttonNine: Button? = null
    private var buttonComma: Button? = null
    private var buttonReset: Button? = null
    private var buttonPlus: Button? = null
    private var buttonMinus: Button? = null
    private var buttonMultiple: Button? = null
    private var buttonDivide: Button? = null
    private var buttonEvaluate: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        buttonReset = findViewById(R.id.button_reset)
        resultView = findViewById(R.id.result_view)
        buttonZero = findViewById(R.id.button_zero)
        buttonOne =findViewById(R.id.button_one)
        buttonTwo = findViewById(R.id.button_two)
        buttonThree =findViewById(R.id.button_three)
        buttonFour =findViewById(R.id.button_four)
        buttonFive = findViewById(R.id.button_five)
        buttonSix =findViewById(R.id.button_six)
        buttonSeven =findViewById(R.id.button_seven)
        buttonEight = findViewById(R.id.button_eight)
        buttonNine =findViewById(R.id.button_nine)
        buttonComma =findViewById(R.id.button_comma)
        buttonPlus =findViewById(R.id.button_plus)
        buttonMinus =findViewById(R.id.button_minus)
        buttonEvaluate = findViewById(R.id.button_evaluate)
        buttonMultiple = findViewById(R.id.button_multiple)
        buttonDivide = findViewById(R.id.button_divide)


        var inOperatorMode: Boolean = false
        var inResultMode: Boolean = false

        val numericButtonClicked = {view: View ->

            val buttonValue = (view as Button).text.toString()
            if (!resultView?.text.toString().equals("0") && inOperatorMode == false
                    && inResultMode == false) {
                    resultView?.text = resultView?.text.toString() + buttonValue
            }
            else {
                inOperatorMode = false
                inResultMode = false
                resultView?.text = buttonValue
            }

        }

        val operatorButtonClicked = {view: View ->
            val buttonValue = (view as Button).text.toString()
            Calculator.addNumber(resultView?.text.toString())
            Calculator.addOperator(buttonValue)
            inOperatorMode = true
        }


        buttonReset?.setOnClickListener({_ ->
            Calculator.reset()
            resultView?.setText("0")
        })

        buttonComma?.setOnClickListener({_ ->
            if (!resultView?.text.toString().contains(char = '.')) {
                resultView?.text = resultView?.text.toString() + ".";
            }
        })

        buttonZero?.setOnClickListener(numericButtonClicked)
        buttonOne?.setOnClickListener(numericButtonClicked)
        buttonTwo?.setOnClickListener(numericButtonClicked)
        buttonThree?.setOnClickListener(numericButtonClicked)
        buttonFour?.setOnClickListener(numericButtonClicked)
        buttonFive?.setOnClickListener(numericButtonClicked)
        buttonSix?.setOnClickListener(numericButtonClicked)
        buttonSeven?.setOnClickListener(numericButtonClicked)
        buttonEight?.setOnClickListener(numericButtonClicked)
        buttonNine?.setOnClickListener(numericButtonClicked)

        buttonPlus?.setOnClickListener(operatorButtonClicked)
        buttonMinus?.setOnClickListener(operatorButtonClicked)
        buttonMultiple?.setOnClickListener(operatorButtonClicked)
        buttonDivide?.setOnClickListener(operatorButtonClicked)

        buttonEvaluate?.setOnClickListener({_ ->
            if (inOperatorMode) {
                Calculator.addNumber("0")
                inOperatorMode = false
            }
            else {
                Calculator.addNumber(resultView?.text.toString())
            }
            Calculator.evaluate()
            resultView?.text = Calculator.result.toString()
            inResultMode = true
            Calculator.reset()
        })


    }
}
