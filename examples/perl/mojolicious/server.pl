#!/usr/bin/perl

use Mojolicious::Lite;

post '/' => sub {
	my $c = shift;

	my $from = $c->param('from');
	my $to   = $c->param('to');

	app->log->debug("from: '$from'");
	app->log->debug("to: '$to'");

	$c->render(text => 'We ♥ perl!');
};

app->start;
