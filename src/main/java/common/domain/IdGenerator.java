package common.domain;

import java.util.HashMap;

public class IdGenerator {
    public static HashMap<String, Long> idHash = new HashMap<>();

    /**
     * ktebe
     * thank you :p
     * aymathaaha hahhahaha
     * aymata bade sir metlak
     * 7otte teffe7a manzu3a ma3 teffe7a mni7a betsir manzu3a metla
     * hahahahahahha echou he
     * ma tele3 ma3e example gher hyda -_-
     * azde innu iza 3mlna hek kaza marra betsiri btt3awade bala ma t2addiyia 3am tdawre 3a experience
     * hahahahahhaha bas leh tle3et teffe7a manzou3a
     * bl 3akes
     * howe bel l asel l 3adet l mni7a ma btente2el lahek l example bass 3an teffe7a say2a -_-
     * mmmm
     * sheftile teffe7a manzu3a saret mni7a masalan
     * 3abele teffe7
     * hhaowhaehahhahah d
     * roure e
     * xhahaahahahah
     * hopwe male bl akel
     * ji3an tayyeb
     * howe aslhaanh ahlahha howe aslan l example 3an l banadoura
     * bass b7eb l teffe7 esh ba3mol :)
     * hahahha oum kol
     * nafe
     * mafe
     * :(
     * uff
     * lyom 3ashiye ba3ed l sala bjib kilo
     * okay
     * lakan hyde l method hiye bta3mol auto generate lal ids
     * ; hashmap hiye memory database l zaghtoura te3ite
     * iza ken 3indi key bzidu we7ed w bredde
     * iza l key mush mawjoud ba3mellou create
     * w berja3 b3ayyet lal method a7san ma erja3 ektob code l rad marten
     * ok ?
     * sheghle hashmap abel ?
     * dunno
     * dictionary bel java script ?
     * assoc arnray boel php
     * sheghle associative array ?
     * noo
     * tyb
     * metzakra kif ya3mol object bel js sa7 ?
     * hek
     * person = {name:"maria",age:36};
     * hyde fina n2oul 3anna assoc array
     * la2anna btekhod key value
     * l keys henne name w age
     * yes
     * l hashmap hek bel java btekhod key value
     * hashmap.put(key,value)
     * person = {name:"maria",age:36}; he bel java hek btenkatab
     * person.put("name","maria");
     * person.put("age",36);
     * aywa
     * l id generator hek by3mol
     * iza marra2te l "of" counters masalan
     * bisir bya3mol hek
     * abel bel hashmap : {"counters":0}
     * id = ids.get("counters"); //bired 0
     * ids.put("counters",id+1);
     * ba3ed bel hashmap
     * {"counters":1}
     * hyde lli b2alb l if
     * bel else mashi bass 3am a3mellu create w 3ayyet lal method
     * iza mush mawjoud l java by3mol exception
     * la2ennu sar mawjoud fa lamma 3ayyet lal method marra tenye ra8 yfout bel if
     * at3et lkahrababa sawene
     * tyt
     * jit
     * fhemtion ?
     * yess
     * tamem
     * @param of
     * @return
     */
    public static Long getId(String of) {

        if (idHash.containsKey(of)) {
            long id = idHash.get(of);
            idHash.put(of, id + 1);
            return id;
        } else {
            idHash.put(of, 1L);
            return getId(of);
        }
    }
}
