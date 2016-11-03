# jabbah
A Java tool for graph-based translation of BPM process into HTN planning domains 

<font face="Verdana, Arial, Helvetica, sans-serif" size="2"><b>What is JABBAH? </b><br>
<br>
JABBAH is a <b>J</b>ava <b>A</b>plication framework for the translation <b>B</b>etween <b>B</b>PM (Business Process Models)&nbsp;<b>A</b>nd <b>H</b>TN-PDDL (Hierarchical Planning Domains).&nbsp;<br>
<br>
The JABBAH system provides a neat tool for analysts that need 
                to perform resource allocation analysis on business workflows, 
                embedding a non-trivial transformation of BPMN-expressed workflows 
                in terms of Hierarchical Task Networks. By providing a fully automated 
                support of the analysis, allowing engineers to exploit the vastly 
                diffused Business Process Management Notation (BPMN) standard 
                for workflow specification, and neatly presenting the results, 
                this system may appeal a very wide and relevant audience. Hencefore, 
                JABBAH may have a considerable potential impact outside the planning 
                community. <br>
<br>
<b>Where can I find further details?</b><br>
<br>
A <a href="http://kti.mff.cuni.cz/%7Ebartak/ICKEPS2009/download/ICKEPS2009_gonzalez-ferrer_p28-37.pdf" rel="nofollow" target="_blank">scientific paper</a> about JABBAH was presented at <a href="http://kti.mff.cuni.cz/%7Ebartak/ICKEPS2009">ICKEPS 2009</a> (<i>Award of excellence</i>), and further improvements were presented in <a href="http://ceur-ws.org/Vol-615" target="_blank">BPM 2010 Demo Track</a>.&nbsp;</font>
<div><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><br>
</font></div>
<div><font face="Verdana, Arial, Helvetica, sans-serif" size="2">A extended scientific paper has been recently published at the <b>Knowledge Engineering Review journal, </b><a href="http://www.ugr.es/~arturogf/Arturo_Gonzalez_Ferrer/mypubs/10_KER-final.pdf" target="_blank">available here</a>.</font>
<div><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><br>
</font></div>
<div><font face="Verdana, Arial, Helvetica, sans-serif" size="2">Have a look at the new <a href="https://sites.google.com/site/bpm2hth/home/screenshots">video screencast</a> as well.<br>
<br>
<b>Who developed it?</b><br>
<br>
<i>Arturo González Ferrer</i> created JABBAH under the supervision of professors <i>Juan Fernández Olivares</i> and <i>Luis Castillo Vidal</i>. See Contact Info for details.<br>
<br>
<br>
</font></div>
</div>

---------------------

Features:
* Import standard XPDL files (tested with TIBCO business studio, XPDL v.2.1)
* Logging system to give detailed output about SEVERE/ERROR/WARNINGs messages.
* Construct equivalent graph model and tree model with jgraph and jgraphT
* Include support for translating different maximally connected components of a process model.
*  Use a specific Block Detection Algorithm for detecting workflow patterns.
* Consider participants and lanes to get activities resource allocation.
* Consider extended attributes 'Duration' (for activities) and 'Lane' (for Participants).
* Translate the tree model into HTN-PDDL domain and problem files

---------------------

Demo:
Have a look at the next screencast, that shows the complete life-cycle of designing a Business Process Model, extract the corresponding HTN planning domain and problem, obtain a plan instance for the process model, and deploy it into a BPMS as Bonita Open Solution, so that it is ubiquitously executed by the participants involved:<br>
<br>

<object width="560" height="315">
  <param name="movie" value="https://www.youtube.com/watch?v=FOHYsMWvS1c"></param>
  <param name="allowFullScreen" value="true"></param>
  <param name="allowscriptaccess" value="always"></param>
  <embed src="http://www.youtube.com/v/aBcDeFg?hl=en_US&amp;version=3" type="application/x-shockwave-flash" width="560" height="315" allowscriptaccess="always" allowfullscreen="true"></embed>
</object>

<br>
<br>
The Graph view, as parsed from XPDL:<br>
<br>
<div style="display:block;text-align:left"><a href="https://sites.google.com/site/bpm2hth/home/screenshots/jabbah1%20.png?attredirects=0" imageanchor="1"><img border="0" height="156" src="https://sites.google.com/site/bpm2hth/home/screenshots/jabbah1%20.png" width="400"></a></div>
<br>
The Nested Process Model, necessary to make the transation to HTN's easier:<br>
<div style="display:block;text-align:left"><a href="https://sites.google.com/site/bpm2hth/home/screenshots/jabbah2%20.png?attredirects=0" imageanchor="1"><img border="0" height="192" src="https://sites.google.com/site/bpm2hth/home/screenshots/jabbah2%20.png" width="400"></a></div>
<br>
<br>
