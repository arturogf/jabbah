#!/usr/bin/perl

my $nota_file = shift || die "No hay nota?";
my @files = @ARGV ; # El resto son ficheros

use File::Slurp qw(read_file write_file);

my $nota = read_file($nota_file) || die "No $nota_file:$!\n";

for my $fichero ( @ARGV ) {
    my $contenido = read_file ( $fichero )  || die "No puedo abrir $fichero:$!\n";
    if ( $contenido !~ m{http://www.gnu.org/licenses/}gs ) {
	$contenido=<<EOC;
/* 
$nota
*/
$contenido
EOC
        write_file( $fichero, $contenido );
    }
    
}
