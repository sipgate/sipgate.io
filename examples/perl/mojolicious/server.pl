#!/usr/bin/perl

use Mojolicious::Lite;

# plugin 'basic_auth';

post '/' => sub {
	my $c = shift;

	# return if not $c->basic_auth({realm => 'Momcorp',
	# 				username => 'bender',
	# 				password => 'rodriguez'});

	my $from = $c->param('from');
	my $to   = $c->param('to');

	app->log->debug("from: '$from'");
	app->log->debug("to: '$to'");

	$c->render(text => 'We ♥ perl!');
};

app->start;
