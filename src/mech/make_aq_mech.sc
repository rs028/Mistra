#!/bin/csh
# called by make_kpp.sc
# edit master_aqueous.eqn file so that identical mechanisms for the aqueous phase are produced
#
# created by Roland von Glasow
# updated in June 2016 by Josue Bock when upgrading KPP

# ======================================================================= #
# Copyright 1996-2017 the Authors
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License along
# with this program; if not, write to the Free Software Foundation, Inc.,
# 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
# ======================================================================= #



# number of aqueous classes to be produced
# ========================================
set nm=("1" "2" "3" "4")

# define filenames
# ================
set in_file=("master_aqueous.eqn")
set out_file=("aq1.eqn" "aq2.eqn" "aq3.eqn" "aq4.eqn")
set head_file=("aer_eqn.head" "tot_eqn12.head" "tot_eqn34.head")
set title_file=("title.1" "title.2" "title.3" "title.4")

# edit mechanism
# ==============
foreach i ($nm)
    echo " duplicating aqueous mechanism for class $i"
    set f_out=$out_file[$i]
#    echo $f_out
# this special quoting ('"$i"') allows use of shell variables in sed
    sed 's/z/'"$i"'/g' $in_file > $f_out
end

# concatenate the different classes to aer.eqn and tot.eqn
# ========================================================

# aerosol
echo " creating equation file for 'aer' mechanism"
cat $head_file[1] $title_file[1] $out_file[1] $title_file[2] $out_file[2] > aer.eqn

# total
echo " creating equation file for 'tot' mechanism"
cat $head_file[2] $title_file[1] $out_file[1] $title_file[2] $out_file[2] $head_file[3] $title_file[3] $out_file[3] $title_file[4] $out_file[4] > tot.eqn

# clean up
# ========

rm -f aq[1-4].eqn
unset nm in_file out_file head_file title_file f_out
