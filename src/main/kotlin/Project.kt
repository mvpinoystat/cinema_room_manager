

fun projectStart(){
    println("Enter the number of rows:")
    val nRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val nCols = readln().toInt()
    println()
    //x and y-axis of bought seat:
    val occupiedRow: MutableList<Int> = mutableListOf()
    val occupiedCol: MutableList<Int> = mutableListOf()
    //income list:
    val incomeTally: MutableList<Int> = mutableListOf()
    val potentialIncome:Int = priceMatrix(nRows,nCols)
    //seating capacity:
    val seatingCapacity:Int = nRows * nCols


    do {
        val selection = showMenu()
        if(selection == 2){
            var flag = true
           while(flag){
               println("Enter a row number:")
               val selectedRowNumber = readln().toInt()
               println("Enter a seat number in that row:")
               val selectedColNumber= readln().toInt()

               if(selectedRowNumber > nRows || selectedRowNumber < 1){
                   println("Wrong input!")
                   continue
               }
               if(selectedColNumber > nCols || selectedColNumber < 1){
                   println("Wrong input!")
                   continue
               }

               if(isSeatVacant(selectedRowNumber,selectedColNumber,occupiedRow,occupiedCol )){
                   occupiedRow.add(selectedRowNumber)
                   occupiedCol.add(selectedColNumber)
                   incomeTally.add(purchaseSeat(nRows,nCols, selectedRowNumber))
                   println()
                   flag = false
               }
               else{
                   println("That ticket has already been purchased!")
                   //the while loop will continue until the flag is false
               }
           }

        }
        if(selection == 1){
            printSeatLocation(nRows,nCols,occupiedRow,occupiedCol)
        }
        if(selection == 3){
            println("Number of purchased tickets: ${incomeTally.size}")
            var percentage: Double = incomeTally.size.toDouble()/seatingCapacity.toDouble()
            //multiply by 100:
            percentage *= 100
            val formatPercentage = String.format("Percentage: %.2f", percentage)
            println("$formatPercentage%")
            var total = 0
            for(i in incomeTally){ total += i}
            println("Current income: $$total")
            println("Total income: $$potentialIncome")
            println()
        }

    } while (selection != 0)

}



fun showMenu(): Int{
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")

    val selectedItem = try{
        when(readln().toInt()){
            1 -> 1
            2 -> 2
            3 -> 3
            0 -> 0
            else -> -1
        }
    }
    catch (e:Exception){
        -1
    }
    return  selectedItem
}

fun isSeatVacant(rowNumber: Int, colNumber: Int,
                 occupiedRowNumber: MutableList<Int>,
                 occupiedColNumber: MutableList<Int>):Boolean{

    var status = true
    for(i in occupiedRowNumber.indices){
        if(occupiedRowNumber[i] == rowNumber && occupiedColNumber[i] == colNumber) {
           status = false
           break
        }
    }
    return status
}
//compute the total potential income
fun priceMatrix(nRows: Int, nCols: Int): Int{

    val seatPriceFront= 10
    val seatPriceBack = 8


    val seatDimension = nRows * nCols
    val totalIncome: Int = if(seatDimension < 60) {
        10 * seatDimension
    }
    else{
        var complexPrice = 0
        if(nRows %2 != 0) {
            val cutOff = nRows/2 + 1
            complexPrice = cutOff * nCols * seatPriceBack
            complexPrice += (nRows - cutOff) * nCols * seatPriceFront
            complexPrice
        }
        else{
            complexPrice = (nRows/2) * nCols * (seatPriceFront + seatPriceBack)
            complexPrice
        }
    }
    return totalIncome
}
fun purchaseSeat(nRows: Int, nCols: Int, rowNumber: Int):Int{

    val seatPriceFront= 10
    val seatPriceBack = 8

    val price: Int = if(nRows * nCols < 60) {
        10
    }
    else{
        if(nRows %2 != 0){
            val cutOff:Int = nRows/2 + 1
            if(rowNumber >= cutOff)
                seatPriceBack
            else seatPriceFront
        }
        else{
            val cutOff:Int = nRows/2
            if(rowNumber > cutOff)
                seatPriceBack
            else seatPriceFront
        }
    }
    println("Ticket Price: $$price")
    return price
}

fun printSeatLocation(nRows: Int, nCols: Int,
                      occupiedRowNumber: MutableList<Int>,
                      occupiedColNumber: MutableList<Int>){

    //create a matrix of seats only:
    val seatMatrix = mutableListOf<MutableList<String>>()
    //create different mutable list objects:
    repeat(nRows){
        seatMatrix.add(MutableList<String>(nCols){"S"})
    }
    /** update seatMatrix based on occupied rows and columns: */
    for(i in occupiedRowNumber.indices){
        seatMatrix[occupiedRowNumber[i]-1][occupiedColNumber[i]-1] = "B"
    }
    println("Cinema:")
    //print the column numbers
    print(" ")
    for(i in 1..nCols){
        print(" $i")
    }
    println()
    //print the rows
    for(i in seatMatrix.indices) {
        println("${i+1} ${seatMatrix[i].joinToString(" ")}")
    }
    println()

}



