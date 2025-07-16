# Build stage
FROM node:16-alpine as build

WORKDIR /app

# 设置 npm 镜像源为淘宝
RUN npm config set registry https://registry.npmmirror.com

# 只复制 package.json 和 yarn.lock 文件
COPY package.json yarn.lock ./

# 安装依赖并缓存
RUN yarn install --frozen-lockfile --cache-folder ./.yarn-cache

# 复制源代码
COPY . .

# 构建应用
RUN yarn build

# Production stage
FROM nginx:alpine

# Copy built assets from build stage
COPY --from=build /app/SmartAuthority /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"] 