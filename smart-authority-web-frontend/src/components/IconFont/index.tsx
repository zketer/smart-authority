import {createFromIconfontCN} from '@ant-design/icons'

console.log('aaaa', PREFIX)
/**
 * @example <IconFont type={'icon-jiantou3'} />
 * */
const IconFont = createFromIconfontCN({
	scriptUrl: PREFIX + '/iconfont/iconfont.js'
})

export default IconFont;