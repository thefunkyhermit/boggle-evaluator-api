## Boggle Evaluator API

This is a Restful application that includes an endpoint that accepts a 'virtual' Boggle Board and will produce all possible words that can be matched.

### Run Locally
To run this locally, run the command `./mvnw spring-boot:run`.  You will need Java/Maven installed in order to do this.

### Developer Notes
This application was built using Java with Spring Boot.  

### Example call
Try try it out in a local environment, try this command:
`curl -X POST localhost:8080/evaluations/en -H 'cache-control: no-cache' -H 'content-type: application/json' -d '{"boardLetters":[ [ "N", "A", "C", "I" ], [ "O", "L", "G", "Y" ], [ "W", "E", "S", "U" ], [ "B", "T", "O", "E" ] ]}'`

Results should return: `{"board":[["N","A","C","I"],["O","L","G","Y"],["W","E","S","U"],["B","T","O","E"]],"results":["eon","canoe","gaol","noels","slag","leg","lags","nag","else","leo","legs","let","tel","noel","ague","noes","lew","segue","betsy","tes","began","leon","al","bets","an","lowest","ao","ales","slow","guest","elan","begs","oleos","sue","olga","be","gus","guy","toe","suo","two","gel","geo","se","bt","oslo","low","web","get","owls","so","ca","toes","owlet","a","st","gusto","b","c","set","sew","e","g","wet","i","aloe","l","n","tb","o","te","oleo","s","t","la","u","cy","ese","lc","w","clan","ages","cyst","le","y","agues","to","owe","lo","owl","beg","use","est","owlets","lets","woe","ouse","bet","cage","lag","cages","won","west","lacy","sot","ale","lao","icy","us","owes","eg","below","el","lest","eo","es","et","woes","guys","lese","glow","no","loan","legacy","gaols","best","we","cal","can","gals","gal","gels","oe","now","tousle","wo","gales","sloe","aloes","gc","on","gi","gale","os","stew","ow","aglow","also","nags","gets","oust","clew","canoes","wets","age","cagy","slew","gust"]}`

### Things to improve
* The application is slow to process a board as it runs O(N2) to traverse through the grid.  If this was to become a commercial use application, I'd probably start measuring how often the same board is evaluated as we may be able to take advantage of caching results in something like Redis.
* Switch to using Spring Validation as we currently aren't really returning proper HTTP Statuses for some errors.  (I ran out of time to implement this).

The application is deployed (via Heroku) to `https://boggle-evaluator-api.herokuapp.com`.

Example Call: curl -X POST https://boggle-evaluator-api.herokuapp.com/evaluations/en -H 'cache-control: no-cache' -H 'content-type: application/json' -d '{"boardLetters":[ [ "N", "A", "C", "I" ], [ "O", "L", "G", "Y" ], [ "W", "E", "S", "U" ], [ "B", "T", "O", "E" ] ]}'

