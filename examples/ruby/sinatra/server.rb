require 'sinatra'

set :port, 3000

post '/' do
  logger.info "=====RECIVED PUSH-API REQUEST====="
  logger.info "from: #{params[:from]}"
  logger.info "to: #{params[:to]}"
  logger.info "=====END PUSH-API REQUEST====="
end

