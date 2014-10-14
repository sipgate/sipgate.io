#!/usr/bin/env perl

use Mojolicious::Lite;
use XML::Simple;

post '/' => sub {
	my $c = shift;

	my $from = $c->param('from');
	my $to   = $c->param('to');

	app->log->debug("from: '$from'");
	app->log->debug("to: '$to'");

	$c->respond_to(
		any => { text => 'We ♥ perl!' },
		xml => { text => XMLout( { Reject => [ { reason => "busy" } ] }, RootName => "Response") }
		# xml => { text => XMLout( { Reject => [ {} ] }, RootName => "Response") }
	);
};

app->start();
