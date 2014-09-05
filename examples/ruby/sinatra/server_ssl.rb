
require 'sinatra/base'
require 'webrick'
require 'webrick/https'
require 'openssl'
require 'sinatra'

SERVER_CERT = "server-cert.pem"
SERVER_KEY = "server-key.pem"
PORT = 1194

module Sinatra
	class Application
		def self.run!
			server_options = {
				:Host => bind,
				:Port => PORT,
				:SSLEnable => true,
				:SSLVerifyClient    => OpenSSL::SSL::VERIFY_NONE,
				:SSLCertificate     => OpenSSL::X509::Certificate.new(File.open(SERVER_CERT).read),
				:SSLPrivateKey      => OpenSSL::PKey::RSA.new(File.open(SERVER_KEY).read),
				:SSLCertName => [[ "CN", WEBrick::Utils::getservername ]],
			}

			Rack::Handler::WEBrick.run self, server_options do |server|
			[:INT, :TERM].each { |sig| trap(sig) { server.stop } }
			server.threaded = settings.threaded if server.respond_to? :threaded=
			set :running, true
			end
		end
	end
end

set :port, PORT
set :ssl_certificate, SERVER_CERT
set :ssl_key, SERVER_KEY

post '/' do
	logger.info "=====RECIVED PUSH-API REQUEST====="
	logger.info "from: #{params[:from]}"
	logger.info "to: #{params[:to]}"
	logger.info "=====END PUSH-API REQUEST====="
end
