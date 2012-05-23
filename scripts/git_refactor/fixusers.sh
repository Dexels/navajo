#!/bin/bash -x

git filter-branch -f --env-filter '
echo AUTHOR: $GIT_AUTHOR_NAME
echo COMMITTER: $GIT_COMMITTER_NAME
echo NAME: $name
echo XXXE: $2
an=${GIT_AUTHOR_NAME}
am=${GIT_AUTHOR_EMAIL}
cn=${GIT_COMMITTER_NAME}
cm=${GIT_COMMITTER_EMAIL}

if [ "$an" = "frank" ]; then
	an="Frank Lyaruu"
	am="frank@dexels.com"
elif [ "$an" = "Dexels CVS migration" ]; then
	echo "ignoring migration"
elif [ "$an" = "dexels" ]; then
	an="Unspecified Dexels employee"
	am="info@dexels.com"
elif [ "$an" = "tjveldhuizen" ]; then
	an="Thijs Jan Veldhuizen"
	am="unknown@sportlink.com"
elif [ "$an" = "ctiecken" ]; then
	an="Carlo Tiecken"
	am="unknown@sportlink.com"
elif [ "$an" = "kvandruten" ]; then
	an="Kjeld van Druten"
	am="unknown@sportlink.com"
elif [ "$an" = "aapeldoorn" ]; then
	an="Ard Apeldoorn"
	am="unknown@sportlink.com"
elif [ "$an" = "sportlink" ]; then
	an="Unknown Sportlink employee"
	am="unknown@sportlink.com"
elif [ "$an" = "hhuizinga" ]; then
	an="Henk Huizinga"
	am="unknown@sportlink.com"
elif [ "$an" = "rhuisman" ]; then
	an="Ron Huisman"
	am="ron.huisman@sportlink.com"
elif [ "$an" = "mverhoef" ]; then
	an="Michiel Verhoef"
	am="michiel.verhoef@sportlink.com"
elif [ "$an" = "mikev" ]; then
	an="Unknown committer"
	am="unknown@dexels.com"
elif [ "$an" = "orion" ]; then
	an="Unknown committer"
	am="unknown@dexels.com"
elif [ "$an" = "ticketing" ]; then
	an="Unknown committer"
	am="unknown@dexels.com"
elif [ "$an" = "plamberti" ]; then
	an="Paul Lamberti"
	am="plamberti@dexels.com"
elif [ "$an" = "meichler" ]; then
	an="Matthew Eichler"
	am="matthew.eichler@aventinesolutions.org"
elif [ "$an" = "mkok" ]; then
	an="Mara Kok"
	am="marakok@gmail.com"
elif [ "$an" = "evdweijden" ]; then
	an="Erik van der Weijden"
	am="evdweijden@dexels.com"
elif [ "$an" = "eversteeg" ]; then
	an="Erik Versteeg"
	am="eversteeg@dexels.com"
elif [ "$an" = "mbergman" ]; then
	an="Martin Bergman"
	am="mbergman@dexels.com"
elif [ "$an" = "jarno" ]; then
	an="Jarno Posthumus"
	am="jarno@dexels.com"
elif [ "$an" = "arjen" ]; then
	an="Arjen Schoneveld"
	am="aschoneveld@dexels.com"
elif [ "$an" = "aphilip" ]; then
	an="Arnoud Philip"
	am="arnoud@dexels.com"
elif [ "$an" = "matthijs" ]; then
	an="Matthijs Philip"
	am="matthijs@dexels.com"
else
	echo "UNKNOWN: ${an}"
fi

cm=${am}
cn=${an}

echo ${am} ${an} 
export GIT_AUTHOR_EMAIL=${am}
export GIT_COMMITTER_NAME=${cn}
export GIT_COMMITTER_EMAIL=${cm}
export GIT_AUTHOR_NAME=${an}
' 

