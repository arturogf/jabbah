(define (domain midominio)

(:requirements
:typing 
:fluents 
:htn-expansion 
:durative-actions 
:negative-preconditions
:universal-preconditions
:disjuntive-preconditions
:conditional-effects
:derived-predicates
:metatags)


(:types
parameter - object
activity - object
participant - object
lane - object
boolean - object
)

(:constants
 true false - boolean
trainingauthors trainingtutorslms contentauthoring contentprocessing creation optimization css flashanimation authorrevision qualityrevision assemblylms registration notification - activity
Optimize - parameter
Training Authoring Formatting GraphicDesign Administration Quality  - lane
)

(:predicates
(completed ?a - activity)
(ordered ?a - activity ?b - activity)
(belongs_to_lane ?p - participant ?l - lane)
(value ?x - parameter ?v - boolean)
)


(:durative-action TRAININGAUTHORS
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'TrainingAuthors' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY TrainingAuthors")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "1")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Training)
:effect (completed trainingauthors))

(:durative-action TRAININGTUTORSLMS
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'TrainingTutorsLMS' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY TrainingTutorsLMS")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "2")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Training)
:effect (completed trainingtutorslms))

(:durative-action CONTENTAUTHORING
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'ContentAuthoring' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY ContentAuthoring")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "3")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Authoring)
:effect (completed contentauthoring))

(:durative-action CONTENTPROCESSING
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'ContentProcessing' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY ContentProcessing")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "4")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Formatting)
:effect (completed contentprocessing))

(:durative-action CREATION
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'Creation' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY Creation")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "5")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed creation))

(:durative-action OPTIMIZATION
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'Optimization' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY Optimization")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "6")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed optimization))

(:durative-action CSS
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'CSS' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY CSS")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "7")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed css))

(:durative-action FLASHANIMATION
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'FlashAnimation' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY FlashAnimation")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "8")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed flashanimation))

(:durative-action AUTHORREVISION
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'AuthorRevision' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY AuthorRevision")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "9")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Authoring)
:effect (completed authorrevision))

(:durative-action QUALITYREVISION
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'QualityRevision' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY QualityRevision")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "10")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 10.0)
:condition(belongs_to_lane ?w Quality)
:effect (completed qualityrevision))

(:durative-action ASSEMBLYLMS
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'AssemblyLMS' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY AssemblyLMS")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "11")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Formatting)
:effect (completed assemblylms))

(:durative-action REGISTRATION
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'Registration' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY Registration")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "12")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 5.0)
:condition(belongs_to_lane ?w Administration)
:effect (completed registration))

(:durative-action NOTIFICATION
:parameters(?w - participant)
:meta (
(:tag prettyprint  "START: ?start; | END: ?end; | DURATION: ?duration; |   'Notification' ALLOCATED TO '?w' ")
(:tag short "ACTIVITY Notification")
(:tag resource "?w")
(:tag monitoring "manual")
(:tag UID "13")
(:tag Type "0")
(:tag OutlineLevel "1")
(:tag OutlineNumber "1")
(:tag WBS "1")
(:tag Summary "0")
)
:duration (= ?duration 5.0)
:condition(belongs_to_lane ?w Administration)
:effect (completed notification))

(:task BlockSB2
:parameters ()
(:method blsb2
:precondition ()
:tasks ((TrainingAuthors ?w1) (BlockPB3) (Notification ?w2) )
))

(:task BlockSB1
:parameters ()
(:method blsb1
:precondition ()
:tasks ((ContentAuthoring ?w1) (ContentProcessing ?w2) (BlockPB1 Optimize) (CSS ?w3) (FlashAnimation ?w4) (BlockPB2) )
))

(:task BlockPB3
:parameters ()
(:method blpb3
:precondition ()
:tasks ([(TRAININGTUTORSLMS ?w1) (BlockSB1) ](REGISTRATION ?w2))
))

(:task BlockPB1
:parameters (?x - parameter)
(:method if_creation
:precondition (value ?x false )
:tasks (CREATION ?w1))
(:method if_optimization
:precondition (value ?x true )
:tasks (OPTIMIZATION ?w1))
)

(:task BlockPB2
:parameters ()
(:method blpb2
:precondition ()
:tasks ([(AUTHORREVISION ?w1) (QUALITYREVISION ?w2) ](ASSEMBLYLMS ?w3))
))


)