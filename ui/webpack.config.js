var config = {
  entry: './index.js',
  output: {
    path: __dirname,
    filename: 'block.build.js',
  },
  devServer: {
    contentBase: './Carousel',
  },
  module: {
    rules: [
      {
        test: /.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
        query: {
          presets: ['@babel/react', '@babel/es2015'],
          plugins: ['@babel/proposal-class-properties'],
        },
      },
    ],
  },
};
module.exports = config;
