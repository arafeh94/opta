ok
inti ma3e
yes
tamem
rakzi ma3e 
awwal shaghle kif l opta byshtghel 
shefte mbere7 kif ken 3am ya3mol assign counter per requirements 
metel ma mshina fion we7ed we7ed mbere7 kenet ghalat
l opta planner bytra7 initial solution 
by3mol assigns la counter kif ma ken fike t2oule randomly 
w by3mol mennon generation
ba3ed ma ya3mol hal generation bimar2a 3al scoring methods 
sou2el , kif by3mol generation w shu bi3arfu shu lezm ya3mol 
l jaweb howe ne7na elnelu lamma 7attaynelu l annotation
l opta planner baddu ya3ne solutions la as2elte 
moujarrad ma eltellu ana shu na2esne howe bi3abbile l na2es men l data lli 
ana 3ate yeha 
masalan iza eltellu na2esne int values bi3abbe int values
iza eltellu na2esne string bi3abbe string
same lal object
iza eltellu na2esne object bi3abbile sou2ele b objects from list ana 3ate <b>already</b> yeha 

so 3indi sou2el w 3indi list of possible solutions 
dayman l number of possible solution henne ps ^ ps , ps = possible solution

tamem lahalla2 ? 
yuh 
<p>ye3ne ma fhemet eno kif bade a3ti possible solutions
masalan hon chou l mafroud a3ti ana </p>
ok
abel ma jewbek 3a hyda ra7 zakrek kif elnelu lal opta shu howe 
lli ana badde ewsallu 
ahamma shi hyda w howe l planning variable 
hyda l ossa kella, lamma ellu lal opta min l planning variable ka2anne 3am 2ellu when sou2ele 
w howe ba2a shaghltu y3abbile yeah, 3an tari2 innu ya3mol generations from of possible solutions 
metzakra lamma 3emel ma3na exception ba3ed ma 3mlnelu run? lamma sa2alna innu baddu range ? <br>
hole henne l possible solutions lli kenu wa2ta list of counters <br>
l opta sar by3ref when lezm y3abbi w shu baddu y3abbe, kelle lli 3le ya3mol generations men l data lli 3ate yeha
w ymar2a 3al scoring method

jewabtek ? 
ye3ne
tyb ta2a3tike example lli 3am neshtghel fi 


3inde requirements la kel fg
w ana hadafe a3te la kel requirement counter 
la2an l relation ben fg w requirement 1 to 1 so lamma a3te counter la req ka2an 3tita la fg

lli ana telbu men opta innu ana fi 3indi variable unknown bel requirement lli howe l counter
se3edne w 7ettele yeah inta
se3a ana b2ellu innu l planning variable howe counter bel requirements 
wel planning entity hiye l requirements (planning entity hiye when l planning variable a3ed)

opta bi2ellek innu ma bymshi l7al bass hek, baddek t2oulile shu lezm 3abbilek l counters, 3tini possible 
solutions (henne list of counters lli ana badde 3abbion)


ba3ed ma 3titiu hal 3 information opta sar fi yeshtghel 

first step : by3mol assign randomly counter to requirement - esma gen1
while (not stopped){
    bimarre2 gen 3al kel lscoring method - by3mol save lal score
    if(new score a7san men older score) update results
    bi3id step 1, 
}

hyda be3te3ek w zyede
sou2el ? 

fhemet tamem

hyda telkhis kif l opta byshtghel halla2 l rules 

sawene 
okii

halla2 mazel 3refte kif l opta sheghal, sar lezm neshtghel bel rules 
awwal rule 3melneha hiye no overlapping 
lba2we lli tel3u brasi (akid issa fi henne hole)

 henne l fg  lli ma n3ammallon assign 3a counter, w shaghlton yna2su l total score,
toul ma l score 3am yen2as kel ma shaja3na l opta innu ma ye2bal hal solution w dawwer 3a solution tene

[relation ben fg w req hiye 1 to 1 -_- eh eh bas bl ekhir nehna mn2oul hayda l fg ma n3amallo 
plan bi ghad l nazar 3an l requirements tab3o ye3ne ma ra7 ne23ad n3edd l requirements]
lli badde ewsallu ana innu watte l score ma aktar
l fekra hiye innu iza ana matloub menne 600 requirement
jewabel 3a 500 mennon 

ya3ne l total score = current score - (600-500)
ah fhemet asdak bas yemkin fg 3ndoun fard req w fg 3ndoun 7 req 
ktebe awlak houwe biyhemo aktar kam req ma3mello assign aw kam fg ma3mello assign
ma bihemna iza bihemmu, ne7na bihemna nkhalle optaplanner ya3tina a7san result, w a7san result innu kel l requirment yelta2alon solution
iza fi shi ma la2alu solution badde ellu lek hyda ghalat w inni watti l score hiye ltari2a l wa7ide
tamemm


khallasna l unplanned halla2 l allOrNone, 
hy shaghlta tshuf iza l fg iza wa7de men l requirement te3ouna ma t2ammanla counter, bna22es l score ktir bshakel innu opta yerfdu w ma yekhdu 7atta ka solution
asdak ma yekhoud l fg ? jit
ma3ek , hummm, azde ken yerfod l solution kellu 
no ma khas l solution hiye l mafroud eno l fg ya y2amenla kl l req ya wala we7de menoun bas ma ye3ne nelghe kel l solution
zakrine nbarrem 3layia hy la2an iza metel ma mfakkr opta 3am yeshtghel sa3eb ttla3 ma3na 
yekhod partial solution 
ok ok l2ita
howe ejbare baddu ya3mol assign,
ra7 ya3tine a7san result howe la2a lal assignments, ya3ne iza ana na22astellou iza ma kenu 7ad ba3ed, ra7 yjarreb dayman ya3mol 7ad ba3ed
fi 7al 3atane result fi mennon fg 3inna counter mannon 7ad ba3ed (fg 1 men asel 7 masalan) be2dar shilon bass ykhalles w ya3tine result
fhemte 3laye ? 
ye3ne ma ktir 
baddek ettesel ? 
no 
tyb 
hy fi meshkle kamena, innu bass ykhalles w ana 22ballu score, ra7 erfod manually l fg lli ma akhadu counter, ya3ne l rule [unplanned] ma ra7 teshtghel
ah
fhemet asdak
bas ma houwe l mafroud eno kel fg ma 3emella assign la req tab3oula kelloun tsir ka2ena unplanned, l unplanning hiye value bten3ata la fg aw la requirement ma t2ammanet ? 
la fg ma n3amal assign lal req tab3oula

```$xslt
[allOrNone]
if not all requirements counter assignment penalize score even further
```
ma mwadda7 iza fg aw requirement, ayel re7let 🤦‍♂ re7le ye3ne ka2eno fg cz hiye flight ma be3te2id kel fg 3nda kaza re7le, eh 3al ghlab hek lakan tanghyr 
esh ra2yek yess ana hayde fhemta eno fg, esh ra2yek lezm nektob, sara7a ana kel l afkar ma btejine ella abel ma nem >_< 
hahahahahahaah
ok fi 3indi rule ghyron


```$xslt
[NoSpaceAllowed]
if there is space between counter for each fg penalize 
```

lmeshkle maria lli 3am temro2 ma3e lahalla2, 
innu lahlla2 ma 7asses 3am 22dar et7akkam bel results ella 3an tari2 l score
w hal shi mush mni7 , akid fi tari2a tenye 
ehh chou l 7all
nestakshef 
fi shaghlten b7es mmken yse3douna <b>hard/soft score, shadow variable</b> we7ed mennon fi ljaweb 
ahaa shadow variable asdak l helper?
ma ba3r3ef esh howe bass mer2a sirtu kaza marra bel docs 
fetna 2rine ? 
yalla







shuf fhemte? 
ma fhemet ella enoun planning variable biyse3douna ta nkhalle l example ysir real aktar
lli fhemtu ana ennon shi ktir sayye2 :) 
hahhaaahhahaahaha
hahahah
la sara7a 7elwe fekrton, henne shadow planning varible bytghayaru kel ma yetghayar l planning varilable l asle,
metel event. when planning varilable changed, change shadow varilable, heda lli badna yeh, lamma ya3mol assign lal counters, ghyrle l value te3 unplanned b fg bshakel innu [rule]
kif ye3ne b chakel eno rule
l rule hiye 7asan shu bytla3na ma3na , l value te3 l unplanned azde,
if kel shi t2amman, unplanned = 0, else unplanned = 1
ahaa
tanshuf kif by3mlula implement


merja3 mnetzakkar when kenna 
3inna 3 rules 




kif badna na3mellon implement
sara7a nsam badane ba3ed he l refused l masekhra >_< 
basita enta mch m2addam 3a gheir jem3at?
akid no 
he addamet 3layia bass la2an l dr alle, w eltellek m2addam w ma badde 
bass he l tari2a l masekhra lli ijet fiyia l refused -_- 
ahh
3am fakker bel rule 
eddemek inti shi fekra 
?


```$xslt
[NoSpaceAllowed]
```


```$xslt
[allOrNone]
```

```
[unplanned] 
```